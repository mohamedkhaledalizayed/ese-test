package com.neqabty.healthcare.pharmacymart.orders.ui.uploadprescription


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
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.view.IMediaSelection
import com.neqabty.healthcare.chefaa.home.view.PickUpImageBottomSheet
import com.neqabty.healthcare.core.data.Constants.pharmacyMartCart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.PhotoUI
import com.neqabty.healthcare.databinding.ActivityPharmacyMartCartBinding
import com.neqabty.healthcare.pharmacymart.address.ui.addresseslist.AddressesActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PharmacyMartCartActivity : BaseActivity<ActivityPharmacyMartCartBinding>(), IMediaSelection {


    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var photoFileName = ""
    lateinit var photoFileURI: Uri
    lateinit var photoPath: String
    private lateinit var  bottomSheetFragment: PickUpImageBottomSheet
    private val mAdapter = PrescriptionsAdapter()
    override fun getViewBinding() = ActivityPharmacyMartCartBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(R.string.order_by_note)

        binding.backBtn.setOnClickListener { finish() }
        binding.recyclerView.adapter = mAdapter
        mAdapter.submitList(pharmacyMartCart.pharmacyMartImageList)

        mAdapter.onItemClickListener = object : PrescriptionsAdapter.OnItemClickListener{
            override fun setOnDeleteClickListener(position: Int) {
                pharmacyMartCart.pharmacyMartImageList.removeAt(position)
                mAdapter.notifyDataSetChanged()
            }
        }

        binding.noteTv.customSelectionActionModeCallback = actionMode
        pharmacyMartCart.orderByText.let { binding.noteTv.setText(it) }
        binding.saveBtn.setOnClickListener {
            if (binding.noteTv.text.toString().isNullOrBlank() && pharmacyMartCart.pharmacyMartImageList.isEmpty()){
                Toast.makeText(this@PharmacyMartCartActivity, "من فضلك اضف روشتة او اكتب طلبك اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            addNote()
            startActivity(Intent(this, AddressesActivity::class.java))
            finish()
        }

        binding.addPrescription.setOnClickListener {
            if (pharmacyMartCart.pharmacyMartImageList.size >= 5){
                Toast.makeText(this, "لا يمكن اضافة اكثر من خمس صور", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            addPhoto()
        }
    }

    override fun onPause() {
        super.onPause()
        if (binding.noteTv.text.toString().isNotEmpty()){
            addNote()
        }
    }

    private fun addNote(){
        pharmacyMartCart.orderByText = binding.noteTv.text.toString()
    }

    private fun addPhoto() {
        bottomSheetFragment = PickUpImageBottomSheet.newInstance(2)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
    }

    private fun grantCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA)
        } else {
            cameraIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                photoPath = photoFile?.path ?: ""
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
        addImageToCart(PhotoUI(photoPath, photoFileName, photoFileURI))
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
            return PhotoUI(f.path, name, Uri.parse(path + "/" + name))
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
        pharmacyMartCart.pharmacyMartImageList.add(photoUI)
        mAdapter.clear()
        mAdapter.submitList(pharmacyMartCart.pharmacyMartImageList)
    }
}