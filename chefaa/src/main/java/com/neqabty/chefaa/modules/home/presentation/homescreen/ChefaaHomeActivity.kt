package com.neqabty.chefaa.modules.home.presentation.homescreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.PhotoUI
import com.neqabty.chefaa.databinding.ChefaaActivityHomeBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.presentation.orderbynote.OrderByNoteActivity
import com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen.OrdersActivity
import com.neqabty.chefaa.modules.products.presentation.SearchActivity
//import com.neqabty.chefaa.modules.CartActivity
//import com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen.OrdersActivity
//import com.neqabty.chefaa.modules.products.presentation.view.productscreen.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChefaaHomeActivity : BaseActivity<ChefaaActivityHomeBinding>() {

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var photoFileName = ""
    lateinit var photoFileURI: Uri
    
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ChefaaActivityHomeBinding.inflate(layoutInflater)
    private lateinit var dialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.chefaa)

        Constants.userNumber = intent.extras!!.getString("user_number", "")
        Constants.mobileNumber = intent.extras!!.getString("mobile_number", "")
        Constants.countryCode = intent.extras!!.getString("country_code", "")
        Constants.nationalID = intent.extras!!.getString("national_id", "")
        Constants.jwt = intent.extras!!.getString("jwt", Constants.jwt)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setMessage(getString(R.string.please_wait))
            .build()

        dialog.show()
        homeViewModel.userRegistered.observe(this) {
            if (it.status) {
                dialog.dismiss()
            }else{
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        homeViewModel.registerUser(Constants.mobileNumber, Constants.userNumber, Constants.countryCode, Constants.nationalID)
    }

    fun findMedications(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun orders(view: View) {
        startActivity(Intent(this, OrdersActivity::class.java))
    }

    fun uploadImage(view: View) {
        if (cart.imageList.size >= 5){
            Toast.makeText(this, "لا يمكن اضافة اكثر من خمس صور", Toast.LENGTH_LONG).show()
            return
        }
        addPhoto()
    }

    fun orderByNote(view: View){
        startActivity(Intent(this,OrderByNoteActivity::class.java))
    }

    private fun selectImage() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
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
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val cartItem = menu.findItem(R.id.menu_item_cart)
        updateCartOptionsMenu(cartItem)

        return super.onCreateOptionsMenu(menu)
    }

    private fun addImageToCart(photoUI: PhotoUI){
        cart.imageList.add(
            OrderItemsEntity(
                image = photoUI.uri?.path!!,
                quantity = 1,
                type = Constants.ITEMTYPES.IMAGE.typeName,
                note = "",
                productId = -1,
                productEntity = null,
                imageUri = photoUI.uri
            )
        )
    }
    
    //region photos

    private fun addPhoto() {
//        val pictureDialog = AlertDialog.Builder(this)
//        pictureDialog.setTitle(getString(R.string.select))
//        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
//        pictureDialog.setItems(pictureDialogItems
//        ) { dialog, which ->
//            when (which) {
//                0 -> galleryIntent()
//                1 -> grantCameraPermission()
//            }
//        }
//        pictureDialog.show()
        galleryIntent()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
    }


    fun grantCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA)
        } else {
            cameraIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
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
        val bos = BufferedOutputStream(FileOutputStream(File(this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES).toString(), photoFileName)))
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        addImageToCart(PhotoUI(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), photoFileName, photoFileURI))
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val path: String = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = Calendar.getInstance().getTimeInMillis().toString() + ".jpg"
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

        return PhotoUI(path, name, null)
    }

    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    //endregion
}