package com.neqabty.healthcare.chefaa

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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.healthcare.chefaa.address.presentation.view.adressscreen.AddressesActivity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.PhotoUI
import com.neqabty.healthcare.databinding.ChefaaActivityCartBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CartActivity : BaseActivity<ChefaaActivityCartBinding>() {

    override fun getViewBinding() = ChefaaActivityCartBinding.inflate(layoutInflater)
    private val mAdapter = CartAdapter()
    private val mItemsAdapter = CartItemsAdapter()
    private lateinit var photoAdapter: PhotosAdapter
    private var totalPrice = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener { finish() }

        binding.continueBtn.setOnClickListener {
            startActivity(Intent(this, AddressesActivity::class.java))
        }
//        setupToolbar(titleResId = R.string.cart)

//        photoAdapter = PhotosAdapter(this)
//        updateView()
//        updatePrice()

//        photoAdapter.onItemClickListener = object :
//            PhotosAdapter.OnItemClickListener {
//            override fun setOnItemClickListener(id: Int) {
//                cart.imageList.removeAt(id)
//                updateView()
//                binding.cartLt.tvNumberImages.visibility = View.GONE
//            }
//        }

        binding.recyclerView.adapter = mAdapter
        binding.itemsRecyclerView.adapter = mItemsAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener() {
                updatePrice()
            }
        }
//        mAdapter.submitList()
////
//        binding.cartLt.photosRv.adapter = photoAdapter
//        binding.cartLt.productRv.adapter = mAdapter
//        (binding.cartLt.productRv.adapter as CartAdapter).registerAdapterDataObserver(object : AdapterDataObserver() {
//            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
//                super.onItemRangeChanged(positionStart, itemCount)
//                if (itemCount == 0)
//                    updateView()
//            }
//        })
//
//        binding.cartLt.deleteNote.setOnClickListener{
//            cart.note = null
//            binding.cartLt.noteTv.setText("")
//            updateView()
//        }

//        binding.cartLt.imageView.setOnClickListener{
//            if (cart.imageList.size >= 5){
//                binding.cartLt.tvNumberImages.visibility = View.VISIBLE
//                Toast.makeText(this, "لا يمكن اضافة اكثر من خمس صور", Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }
//            galleryIntent()
//        }
//
//        binding.cartLt.selectAddress.setOnClickListener {
//            startActivity(Intent(this, AddressesActivity::class.java))
//        }

    }

//    override fun onResume() {
//        super.onResume()
//        if (Constants.selectedAddress != null){
//            binding.cartLt.selectAddress.text = Constants.selectedAddress?.address
//        }else{
//            binding.cartLt.selectAddress.text = "إختر عنوان"
//        }
//    }

    private fun updatePrice() {
        totalPrice = 0.0
        for (item in cart.productList){
            totalPrice += item.productEntity?.price!!.times(item.quantity.toDouble())
        }

//        binding.cartLt.subTotalValue.text = "$totalPrice جنيه"
//        binding.cartLt.totalValue.text = "$totalPrice جنيه"
    }

    private val SELECT_FILE = 1

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
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
        }
    }

    private fun grantCameraPermission() {
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
    private var photoFileName = ""
    lateinit var photoFileURI: Uri
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

    private val REQUEST_CAMERA = 0

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

        photoAdapter.notifyDataSetChanged()
    }

    private fun saveImage(myBitmap: Bitmap): PhotoUI {
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

//    private fun updateView() {
//        ///// checkout btn and Empty view
//        if (cart.size == 0){
//            binding.clEmptyCart.visibility = View.VISIBLE
//            binding.cartLt.checkout.visibility = View.GONE
//        }else{
//            binding.clEmptyCart.visibility = View.GONE
//            binding.cartLt.checkout.visibility = View.VISIBLE
//        }
//
//        /////Images recyclerView
//        if (cart.imageList.isNotEmpty()) {
//            binding.cartLt.photosRv.visibility = View.VISIBLE
//            photoAdapter.submitList()
//        } else {
//            binding.cartLt.llPhotos.visibility = View.GONE
//        }
//
//        /////Products recyclerView
//        if(cart.productList.isNotEmpty()) {
//            binding.cartLt.productRv.visibility = View.VISIBLE
//            mAdapter.submitList()
//        } else {
//            binding.cartLt.llProducts.visibility = View.GONE
//        }
//
//
//        if(cart.note != null){
//            binding.cartLt.noteTv.visibility = View.VISIBLE
//            binding.cartLt.noteTv.setText(cart.note!!.note)
//        } else {
//            binding.cartLt.clNote.visibility = View.GONE
//        }
//
//    }

//    fun checkOut(view: View) {
//        // save note to cart
//        if (totalPrice < 300.0  && cart.imageList.isEmpty() && cart.note?.note.isNullOrEmpty()){
//            Toast.makeText(this, "يجب الا تقل قيمة الطلب عن 300 جنيه.", Toast.LENGTH_LONG).show()
//            return
//        }
//        if (!binding.cartLt.noteTv.text.toString().isNullOrBlank()) {
//            cart.note = OrderItemsEntity(
//                type = Constants.ITEMTYPES.NOTE.typeName,
//                quantity = 1,
//                image = "",
//                note = binding.cartLt.noteTv.text.toString(),
//                productId = -1,
//                productEntity = null,
//                imageUri = null
//            )
//        }
//
//        if (Constants.selectedAddress == null){
//            Toast.makeText(this, "من فضلك إختر عنوان.", Toast.LENGTH_LONG).show()
//        }else{
//
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions()
//                return
//            }
//
//            if (!checkGPS()){
//                buildAlertMessageNoGps()
//                return
//            }
//
//
//
//            startActivity(Intent(this, CheckOutActivity::class.java))
//        }
//    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        reLaunchHomeActivity(this)
//    }
}