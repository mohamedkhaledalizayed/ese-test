package com.neqabty.healthcare.core.packages


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
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.listOfFollowers
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityUploadFrontNationalidBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.Followers
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.FollowerAdapter
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.PhotoUI
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class UploadFrontNationalid : BaseActivity<ActivityUploadFrontNationalidBinding>() {
    private var userImageUri: String? = null
    private var nationalIdFrontUri: String? = null
    private var nationalIdBackUri: String? = null
    private var mAdapter = FollowerAdapter()
    private val subscriptionViewModel: SubscriptionViewModel by viewModels()
    override fun getViewBinding() = ActivityUploadFrontNationalidBinding.inflate(layoutInflater)
    private var REQUEST_CODE = 0
    private lateinit var packageDetails: PackagesEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "بيانات الحساب")
        packageDetails = intent.extras?.getParcelable<PackagesEntity>("package")!!


        binding.followersRecycler.adapter = mAdapter

        mAdapter.onItemClickListener = object : FollowerAdapter.OnItemClickListener{
            override fun setOnDeleteClickListener(position: Int) {
                listOfFollowers.removeAt(position)
                mAdapter.submitList(listOfFollowers)
                binding.addFollowerBtn.visibility = View.VISIBLE
            }

        }
        binding.ivFront.setOnClickListener {
            REQUEST_CODE = if (userImageUri != null){
                1003
            }else{
                1002
            }
            checkPermissionsAndOpenFilePicker()
        }
        binding.btnBackId.setOnClickListener {
            binding.ivFront.setImageResource(R.drawable.img_nat_id_front)
            binding.tvEnterNationalIdFront.text = resources.getString(R.string.enter_id_back)
            binding.btnBackId.visibility = View.GONE
            binding.addFollowerBtn.visibility = View.VISIBLE
            binding.bPayNow.visibility = View.VISIBLE
//            startActivity(Intent(this, UploadBackNationalid::class.java))
        }

        binding.addFollowerBtn.setOnClickListener {
            startActivity(Intent(this, AddFollowersActivity::class.java))
        }

        binding.bPayNow.setOnClickListener {
            payNow()
        }

        subscriptionViewModel.providers.observe(this) {

            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
//                        binding.progressCircular.visibility = View.VISIBLE
//                        binding.screenContainer.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
//                        binding.progressCircular.visibility = View.GONE
//                        binding.screenContainer.visibility = View.VISIBLE
                        if (resource.data!!.status){
                            Toast.makeText(this, "تم الأشتراك بنجاح.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, SehaPaymentActivity::class.java)
                            intent.putExtra("name", packageDetails.name)
                            intent.putExtra("price", packageDetails.price)
                            intent.putExtra("vat", packageDetails.vat)
                            intent.putExtra("total", packageDetails.total)
                            intent.putExtra("serviceCode", packageDetails.serviceCode)
                            intent.putExtra("serviceActionCode", packageDetails.serviceActionCode)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
//                        binding.progressCircular.visibility = View.GONE
//                        binding.screenContainer.visibility = View.VISIBLE
                    }
                }

            }
        }

    }

    private fun payNow() {

        val sub = SubscribePostBodyRequest(
                name = intent.getStringExtra("name")!!,
                email = intent.getStringExtra("email")!!,
                birthDate = intent.getStringExtra("birthDate")!!,
                deliveryPhone = intent.getStringExtra("deliveryPhone")!!,
                address = intent.getStringExtra("address")!!,
                job = intent.getStringExtra("job")!!,
                mobile = sharedPreferences.mobile,
                nationalId = intent.getStringExtra("nationalId")!!,
                entityCode = sharedPreferences.code,
                serviceActionCode = packageDetails.serviceActionCode!!,
                referralNumber = "",
                personalImage = userImageUri!!,
                frontIdImage = nationalIdFrontUri!!,
                backIdImage = nationalIdBackUri!!,
                followers = listOfFollowers.toList()
            )

            subscriptionViewModel.addSubscription(sub)

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

    override fun onResume() {
        super.onResume()
        mAdapter.submitList(listOfFollowers)
        if (packageDetails.maxFollower == mAdapter.itemCount){
            binding.addFollowerBtn.visibility = View.GONE
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
                    Toast.makeText(this@UploadFrontNationalid, "صورة كبيرة الحجم.", Toast.LENGTH_LONG).show()
                    return
                }
                val photoUI = saveImage(bitmap)

                val file = File(photoUI.path, photoUI.name)
                val res = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
                when (REQUEST_CODE) {
                    1001 -> {
                            userImageUri = res
                            binding.ivFront.setImageURI(data.data)
//                            binding.personalPhotoText.text = "تم إرفاق الصورة بنجاح."
                    }
                    1002 -> {
                        userImageUri = res
                            nationalIdFrontUri = res
                            binding.ivFront.setImageURI(data.data)
//                            binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                    }
                    1003 -> {
                            nationalIdBackUri = res
                            binding.ivFront.setImageURI(data.data)
//                            binding.addImageTextBack.text = "تم إرفاق الصورة بنجاح."
                    }
                    1004 -> {
//                            followerImageUri = data.data!!
//                            followerUri = res
//                            binding.followerImage.setImageURI(data.data!!)
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

}