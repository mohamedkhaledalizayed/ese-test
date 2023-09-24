package com.neqabty.healthcare.pharmacymart.home.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.view.IMediaSelection
import com.neqabty.healthcare.chefaa.home.view.PickUpImageBottomSheet
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.PhotoUI
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyMartHomeBinding
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrderEntity
import com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails.PharmacyMartOrderDetailsActivity
import com.neqabty.healthcare.pharmacymart.orders.ui.uploadprescription.PharmacyMartCartActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PharmacyMartHomeActivity : BaseActivity<ActivityPharmacyMartHomeBinding>(), IMediaSelection {

    private val SELECT_FILE = 1
    private var photoFileName = ""
    private val REQUEST_CAMERA = 0
    lateinit var photoFileURI: Uri
    private lateinit var dialog: AlertDialog
    private val viewModel: PharmacyMartHomeViewModel by viewModels()
    private lateinit var  bottomSheetFragment: PickUpImageBottomSheet
    private var mAdapter: PharmacyMartOrdersAdapter = PharmacyMartOrdersAdapter()
    override fun getViewBinding() = ActivityPharmacyMartHomeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addPrescription.setOnClickListener {
            if (Constants.pharmacyMartCart.pharmacyMartImageList.size >= 5){
                Toast.makeText(this, "لا يمكن اضافة اكثر من خمس صور", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            addPhoto()
        }

        binding.writeOrderContainer.setOnClickListener {
            startActivity(Intent(this, PharmacyMartCartActivity::class.java))
        }

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setMessage(getString(R.string.please_wait))
            .build()


        viewModel.orders.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.status){
                            mAdapter.submitList(resource.data.data)
                        }else{
                            binding.noPreviousOrders.visibility = View.VISIBLE
                            binding.noPreviousOrdersText.visibility = View.VISIBLE
                            Toast.makeText(this@PharmacyMartHomeActivity, resource.data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this@PharmacyMartHomeActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.ordersRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            PharmacyMartOrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(orderEntity: OrderEntity) {
                val intent = Intent(this@PharmacyMartHomeActivity, PharmacyMartOrderDetailsActivity::class.java)
                intent.putExtra("orderId", orderEntity.id)
                startActivity(intent)
            }
        }

        viewModel.registerUser()
        viewModel.userRegistered.observe(this) {
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.hide()
                    if (it.data!!.status){
                        viewModel.getOrders()
                    }else{
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                Status.ERROR ->{
                    dialog.hide()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == SELECT_FILE)
                    data?.let { onSelectFromGalleryResult(data) }
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult()
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "لم يتم اختيار اى صورة.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addImageToCart(photoUI: PhotoUI){
        Constants.pharmacyMartCart.pharmacyMartImageList.add(photoUI.uri)
        startActivity(Intent(this, PharmacyMartCartActivity::class.java))
    }

    private fun addPhoto() {
        bottomSheetFragment = PickUpImageBottomSheet.newInstance(0)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
    }

    private fun grantCameraPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    cameraIntent()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    dialog.dismiss()
                    showSettingsDialog("يحتاج هذا التطبيق الاذن للوصول الى الكاميرا. يمكنك منحهم في إعدادات التطبيق.")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun cameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(this)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.neqabty.healthcare.provider",
                        it
                    )
                    photoFileName = photoFile.name
                    photoFileURI = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                }
            }

        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
            var bitmapSize = bitmap.allocationByteCount
            if (bitmapSize / (1024 * 1024) > 95){
                Toast.makeText(this, "صورة كبيرة الحجم.", Toast.LENGTH_LONG).show()
                return
            }
            val photoUI = saveImage(bitmap)
            addImageToCart(photoUI)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        val bitmap: Bitmap = BitmapFactory.decodeFile(this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES).toString() + "/" + photoFileName)
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val bos = BufferedOutputStream(
            FileOutputStream(
                File(this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES).toString(), photoFileName)
            )
        )
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        addImageToCart(PhotoUI(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), photoFileName, photoFileURI))
    }

    private fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val path: String = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = Calendar.getInstance().timeInMillis.toString() + ".jpg"
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

        return PhotoUI(path, name, null)
    }

    private fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    override fun onCameraSelected() {
        grantCameraPermission()
        bottomSheetFragment.dismiss()
    }

    override fun onGallerySelected() {
        galleryIntent()
        bottomSheetFragment.dismiss()
    }

}