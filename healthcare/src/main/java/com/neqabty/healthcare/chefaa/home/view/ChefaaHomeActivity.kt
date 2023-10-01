package com.neqabty.healthcare.chefaa.home.view



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
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.cart.CartActivity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.chefaa.orders.presentation.orderbynote.OrderByNoteActivity
import com.neqabty.healthcare.chefaa.orders.presentation.view.orderdetailscreen.OrderDetailsActivity
import com.neqabty.healthcare.chefaa.products.presentation.SearchActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.CHEFAA_SUPPORT_NUMBER
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.data.Constants.countryCode
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.core.data.Constants.name
import com.neqabty.healthcare.core.data.Constants.nationalID
import com.neqabty.healthcare.core.data.Constants.userNumber
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.*
import com.neqabty.healthcare.databinding.ChefaaActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChefaaHomeActivity : BaseActivity<ChefaaActivityHomeBinding>(), IMediaSelection {

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var photoFileName = ""
    lateinit var photoFileURI: Uri
    private val  bottomSheetFragment: PickUpImageBottomSheet by lazy { PickUpImageBottomSheet() }
    private val mAdapter: OrdersAdapter = OrdersAdapter()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ChefaaActivityHomeBinding.inflate(layoutInflater)
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.chefaa)

        userNumber = intent.extras!!.getString("user_number", "")
        mobileNumber = intent.extras!!.getString("mobile_number", "")
        countryCode = intent.extras!!.getString("country_code", "")
        nationalID = intent.extras!!.getString("national_id", "")
        name = intent.extras!!.getString("name", "")
        Constants.jwt = intent.extras!!.getString("jwt", Constants.jwt)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setMessage(getString(R.string.please_wait))
            .build()

        homeViewModel.getOrders(mobileNumber, 10, 1)
        homeViewModel.orders.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.status){
                            mAdapter.submitList(resource.data.data?.data)
                        }else{
                            binding.noPreviousOrders.visibility = View.VISIBLE
                            binding.noPreviousOrdersText.visibility = View.VISIBLE
                            Toast.makeText(this@ChefaaHomeActivity, resource.data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this@ChefaaHomeActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.ordersRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(orderEntity: OrderEntity) {
                val intent = Intent(this@ChefaaHomeActivity, OrderDetailsActivity::class.java)
                intent.putExtra("orderId", orderEntity)
                startActivity(intent)
            }

            override fun setOnCallClickListener() {
                AppUtils().call(this@ChefaaHomeActivity, CHEFAA_SUPPORT_NUMBER)
            }
        }

        homeViewModel.registerUser(mobileNumber, userNumber, countryCode, nationalID, name)
        homeViewModel.userRegistered.observe(this) {
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.hide()
                    if (!it.data!!.status){
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                Status.ERROR ->{
                    dialog.hide()
                }
            }
        }

        binding.searchAboutMedicine.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.addPrescription.setOnClickListener {
            if (cart.productList.isNotEmpty()){
                showWarning("فى حالة اضافة روشتة سوف يتم حذف المنتجات من عربة التسوق.")
                return@setOnClickListener
            }
            if (cart.imageList.size >= 5){
                Toast.makeText(this, "لا يمكن اضافة اكثر من خمس صور", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            addPhoto()
        }

        binding.writeOrderContainer.setOnClickListener {
            if (cart.productList.isNotEmpty()){
                showWarning("فى حالة كتابة طلب سوف يتم حذف المنتجات من عربة التسوق.")
                return@setOnClickListener
            }
            startActivity(Intent(this, OrderByNoteActivity::class.java))
        }

        binding.cart.setOnClickListener {
            startActivity(Intent(this@ChefaaHomeActivity, CartActivity::class.java))
        }

        binding.backBtn.setOnClickListener { finish() }

        binding.contact.setOnClickListener {
            AppUtils().call(this, CHEFAA_SUPPORT_NUMBER)
        }

    }

    private fun showWarning(message: String, title: String = getString(R.string.alert_title)) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
           cart.productList.clear()
            Toast.makeText(this, "تم حذف جميع المنتجات من عربة التسوق بنجاخ.", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no_btn)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        if (cart.size != 0){
            binding.cart.setBackgroundResource(R.drawable.cart_icon)
        }else{
            binding.cart.setBackgroundResource(R.drawable.empty_cart)
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
        startActivity(Intent(this, OrderByNoteActivity::class.java))
    }

    //region photos

    private fun addPhoto() {
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
    }

    private fun grantCameraPermission() {
        PermissionUtils.requestCameraPermission(this, object : PermissionsCallback {
            override fun onPermissionRequest(granted: Boolean) {
                if (granted) {
                    cameraIntent()
                } else {
                    dialog.dismiss()
                    showSettingsDialog("يحتاج هذا التطبيق الاذن للوصول الى الكاميرا. يمكنك منحهم في إعدادات التطبيق.")
                }
            }
        })
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
        val bos = BufferedOutputStream(FileOutputStream(File(this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES).toString(), photoFileName)))
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