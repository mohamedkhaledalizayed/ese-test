package com.neqabty.meganeqabty.profile.view.update

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.utils.FileUtils
import com.neqabty.meganeqabty.databinding.ActivityUpdateInfoBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


@AndroidEntryPoint
class UpdateInfoActivity : BaseActivity<ActivityUpdateInfoBinding>() {
    override fun getViewBinding() = ActivityUpdateInfoBinding.inflate(layoutInflater)
    private val REQUEST_CODE = 101
    private val MINISTRY_LICENSE = 150
    private val MEMBERSHIP_CARD = 250
    private var imageType = 0
    private var imageUrl: Uri? = null
    private val profileViewModel: UpdateProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.update_data)

        if (intent.getIntExtra("key", 0) == 200){
            binding.cardContainer.visibility = View.VISIBLE
            binding.licenceContainer.visibility = View.GONE
        }else{
            binding.licenceContainer.visibility = View.VISIBLE
            binding.cardContainer.visibility = View.GONE
        }
        profileViewModel.membershipCard.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progress.visibility = View.GONE
                        binding.linearLayout.visibility = View.GONE
                        binding.statusImage.visibility = View.VISIBLE
                        binding.messageText.visibility = View.VISIBLE
                        imageUrl = null

                        Toast.makeText(this, resources.getString(R.string.order_sent), Toast.LENGTH_LONG).show()
                        Picasso.get().load(R.drawable.success).into(binding.statusImage)
                        binding.messageText.text = resources.getString(R.string.order_sent)

                    }
                    Status.ERROR -> {
                        binding.progress.visibility = View.GONE
                        binding.linearLayout.visibility = View.GONE
                        binding.statusImage.visibility = View.VISIBLE
                        binding.messageText.visibility = View.VISIBLE
                        Picasso.get().load(R.drawable.cancel).into(binding.statusImage)
                        binding.messageText.text = resources.getString(R.string.order_not_sent)
                        imageUrl = null
                        Toast.makeText(this, resources.getString(R.string.order_not_sent), Toast.LENGTH_LONG).show()

                    }
                }
            }


        }

        profileViewModel.ministryLicense.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.image.visibility = View.GONE
                        binding.status.visibility = View.VISIBLE
                        binding.message.visibility = View.VISIBLE
                        binding.sendOrder.isEnabled = false
                        imageUrl = null

                        Picasso.get().load(R.drawable.success).into(binding.status)
                        binding.message.text = resources.getString(R.string.order_sent)

                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.image.visibility = View.GONE
                        binding.status.visibility = View.VISIBLE
                        binding.message.visibility = View.VISIBLE
                        Picasso.get().load(R.drawable.cancel).into(binding.status)
                        binding.message.text = resources.getString(R.string.order_not_sent)
                        binding.sendOrder.isEnabled = false
                        imageUrl = null
                    }
                }
            }


        }

    }

    fun membershipOrder(view: View) {
        if (imageType == MINISTRY_LICENSE){
            Toast.makeText(this, "من فضلك اختر صورة شخصية.", Toast.LENGTH_LONG).show()
            return
        }

        if (imageUrl == null){
            Toast.makeText(this, "من فضلك اختر صورة شخصية.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.mobile.text.isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الموبايل.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.address.text.isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل العنوان.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.year.text.isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل السنة.", Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.year.text.isDigitsOnly()){
            Toast.makeText(this, "من فضلك ادخل السنة.", Toast.LENGTH_LONG).show()
            return
        }

        val photo: MultipartBody.Part = prepareFilePart("photo", imageUrl!!)

        profileViewModel.uploadMembershipCard("Token ${sharedPreferences.token}",
            binding.mobile.text.toString(),
            binding.address.text.toString(),
            Integer.parseInt(binding.year.text.toString()),
            photo)


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


        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
    }

    fun ministryLicenseOrder(view: View) {
        if (imageType == MEMBERSHIP_CARD){
            Toast.makeText(this, getString(R.string.select_licence_pic), Toast.LENGTH_LONG).show()
            return
        }

        if (imageUrl == null){
            Toast.makeText(this, getString(R.string.select_licence_pic), Toast.LENGTH_LONG).show()
            return
        }

        val license: MultipartBody.Part = prepareFilePart("license", imageUrl!!)
        profileViewModel.uploadMinistryLicense("Token ${sharedPreferences.token}", license)
    }

    fun addMemberImage(view: View) {
        imageType = MEMBERSHIP_CARD
        checkPermissionsAndOpenFilePicker()
    }

    fun addImage(view: View) {
        imageType = MINISTRY_LICENSE
        checkPermissionsAndOpenFilePicker()
    }

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
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(
            Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUrl = data.data
            if (imageType == MINISTRY_LICENSE){
                binding.image.setImageURI(imageUrl)
            }else{
                binding.personalImage.text = getString(R.string.image_uploaded)
                Picasso.get().load(R.drawable.success).into(binding.icon)
            }
        }
    }
}