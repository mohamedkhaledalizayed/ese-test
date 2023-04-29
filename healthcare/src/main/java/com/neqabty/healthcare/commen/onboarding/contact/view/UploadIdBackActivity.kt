package com.neqabty.healthcare.commen.onboarding.contact.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.contact.data.source.OcrData
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.FileUtils
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityUploadIdBackBinding
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.PhotoUI
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class UploadIdBackActivity : BaseActivity<ActivityUploadIdBackBinding>() {

    private var REQUEST_CODE = 1001
    private var nationalIdBack: PhotoUI? = null

    val uploadIdBackViewModel: UploadIdBackViewModel by viewModels()
    override fun getViewBinding() = ActivityUploadIdBackBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.complete_profile)
        initializeViews()
    }

    private fun initializeViews() {
        binding.ivBack.setOnClickListener{
            checkPermissionsAndOpenFilePicker()
        }
        binding.bNext.setOnClickListener {
            if (nationalIdBack == null) {
                Toast.makeText(this, getString(R.string.enter_id_back), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

//            createOcr()
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    private fun createOcr() {
        val frontImg: MultipartBody.Part = prepareFilePart(OcrData.front?.name!! , OcrData.front?.uri!!)
        val backImg: MultipartBody.Part = prepareFilePart(OcrData.back?.name!! , OcrData.back?.uri!!)
        uploadIdBackViewModel.createOcr(frontImg, backImg, sharedPreferences.nationalId, sharedPreferences.mobile)

        uploadIdBackViewModel.createOcrStatus.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null){
                            //check later
                            showAlert("submitted successfully, check later"){
                                //TODO
                                navigate()
                            }

                        }else{
                            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //region


    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissionsAndOpenFilePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, permission) !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    12
                )
            }
        } else {
            getImage()
        }
    }

    private fun getImage(){
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE){
                onSelectFromGalleryResult(data!!)
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        if (data != null) {
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, data.data)

                var bitmapSize = bitmap.allocationByteCount
                if (bitmapSize / (1024 * 1024) > 95){
                    Toast.makeText(this, "صورة كبيرة الحجم.", Toast.LENGTH_LONG).show()
                    return
                }
                val photoUI = saveImage(bitmap)

                val file = File(photoUI.path, photoUI.name)
                OcrData.back = photoUI//Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
                when (REQUEST_CODE) {
                    1001 -> {
                        nationalIdBack = photoUI
                        binding.ivBack.setImageBitmap(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }}

    private fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val path: String = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = System.currentTimeMillis().toString() + ".jpg"
        val directory = File(path)
        if (!directory.exists())
            directory.mkdirs()
        try {
            val f = File(directory, name)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
            return PhotoUI(path, name, Uri.parse(path + "/" + name))
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return PhotoUI(path, name,null)
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @NonNull
    private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part { // use the FileUtils to get the actual file by uri
        val file = FileUtils.getFile(this, fileUri)
        // create RequestBody instance from file
        val requestFile = RequestBody.create(
            contentResolver.getType(fileUri).toString().toMediaTypeOrNull(),
            file
        )


        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun navigate() {
        val mainIntent = Intent(
            this,
            ReviewYourDataActivity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}