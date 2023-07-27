package com.neqabty.healthcare.onboarding.contact.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.FileUtils
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityUploadIdBackBinding
import com.neqabty.healthcare.onboarding.contact.data.source.OcrData
import com.neqabty.healthcare.packages.subscription.view.PhotoUI
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
        initializeObservers()
    }

    private fun initializeViews() {
        binding.ivBack.setOnClickListener {
            checkPermissionsAndOpenFilePicker()
        }
        binding.bNext.setOnClickListener {
            if (nationalIdBack == null) {
                Toast.makeText(this, getString(R.string.enter_id_back), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            createOcr()
        }
    }

    private fun initializeObservers() {
        uploadIdBackViewModel.createOcrStatus.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
                            //check later
                            showAlert(getString(R.string.request_sent)) {
                                showWaitingProgressbar()
                            }

                        } else {
                            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        uploadIdBackViewModel.checkMemberStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
                            if (resource.data.ocrStatus.equals("pending")) {
                                showWaitingProgressbar()
                            } else { // OCR completed
                                sharedPreferences.isContactSubscriber = true
                                navigate()
                            }
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        showWaitingProgressbar()
                    }
                }
            }
        }
    }

    private fun createOcr() {
        val frontImg: MultipartBody.Part = prepareFilePart("id_face", OcrData.front?.uri!!)
        val backImg: MultipartBody.Part = prepareFilePart("id_back", OcrData.back?.uri!!)
        uploadIdBackViewModel.createOcr(
            frontImg,
            backImg,
            sharedPreferences.nationalId,
            sharedPreferences.mobile
        )
    }

    private fun showWaitingProgressbar() {
        binding.clProgress.visibility = View.VISIBLE
        val totalProgress = 100
        val durationInMillis = 30000L // 1 minute in milliseconds

        val countDownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress =
                    ((durationInMillis - millisUntilFinished) * totalProgress / durationInMillis).toInt()
                binding.progressBar.progress = progress
                binding.tvProgress.text = progress.toString() + "% completed"
            }

            override fun onFinish() {
                binding.progressBar.progress = totalProgress
                binding.clProgress.visibility = View.GONE
                checkOcrStatus()
            }
        }

        countDownTimer.start()
    }

    private fun checkOcrStatus() {
        uploadIdBackViewModel.checkMemberStatus(nationalId = sharedPreferences.nationalId)
    }

    //region


    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissionsAndOpenFilePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT)
                    .show()
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

    private fun getImage() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        i.type = "image/*"
        startActivityForResult(i, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
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
                if (bitmapSize / (1024 * 1024) > 95) {
                    Toast.makeText(this, "صورة كبيرة الحجم.", Toast.LENGTH_LONG).show()
                    return
                }
                val photoUI = saveImage(bitmap)

                OcrData.back = com.neqabty.healthcare.core.utils.PhotoUI("", "", data.data)
                when (REQUEST_CODE) {
                    1001 -> {
                        nationalIdBack = photoUI
                        binding.ivBack.setImageBitmap(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

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
            MediaScannerConnection.scanFile(
                this,
                arrayOf(f.path),
                arrayOf("image/jpeg"),
                null
            )
            fo.close()
            return PhotoUI(path, name, Uri.parse(path + "/" + name))
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return PhotoUI(path, name, null)
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

    private fun navigate() {
        val mainIntent = Intent(
            this,
            ReviewYourDataActivity::class.java
        )
        mainIntent.putExtra("address", uploadIdBackViewModel.checkMemberStatus.value?.data?.ocrs?.get(1)?.result?.extractedInfo?.street + " "+uploadIdBackViewModel.checkMemberStatus.value?.data?.ocrs?.get(1)?.result?.extractedInfo?.governorate)
        startActivity(mainIntent)
        finishAffinity()
    }
// endregion
}