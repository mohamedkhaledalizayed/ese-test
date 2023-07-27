package com.neqabty.healthcare.onboarding.contact.view

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
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.FileUtils
import com.neqabty.healthcare.core.utils.PhotoUI
import com.neqabty.healthcare.databinding.ActivityUploadIdFrontBinding
import com.neqabty.healthcare.onboarding.contact.data.source.OcrData
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class UploadIdFrontActivity : BaseActivity<ActivityUploadIdFrontBinding>() {

    private var REQUEST_CODE = 1001
    private var nationalIdFront: PhotoUI? = null

    override fun getViewBinding() = ActivityUploadIdFrontBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.complete_profile)
        initializeViews()
    }

    private fun initializeViews() {
        binding.ivFront.setOnClickListener{
            checkPermissionsAndOpenFilePicker()
        }
        binding.bNext.setOnClickListener {
            if (nationalIdFront == null) {
                Toast.makeText(this, getString(R.string.enter_id_front), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val mainIntent = Intent(
                this,
                UploadIdBackActivity::class.java
            )
            startActivity(mainIntent)
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
        i.type = "image/*"
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

                OcrData.front = PhotoUI("", "", data.data) //Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
                when (REQUEST_CODE) {
                    1001 -> {
                        nationalIdFront = photoUI
                        binding.ivFront.setImageBitmap(bitmap)
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
            MediaScannerConnection.scanFile(this, arrayOf(f.path), arrayOf("image/jpeg"), null)
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
        val requestFile = file
            .asRequestBody(contentResolver.getType(fileUri).toString().toMediaTypeOrNull())


        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
// endregion
}