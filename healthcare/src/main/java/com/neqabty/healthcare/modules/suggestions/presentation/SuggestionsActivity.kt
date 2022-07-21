package com.neqabty.healthcare.modules.suggestions.presentation


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySuggestionsBinding
import com.neqabty.healthcare.modules.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.modules.suggestions.domain.entity.CategoryEntity
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class SuggestionsActivity : BaseActivity<ActivitySuggestionsBinding>() {

    private var REQUEST_CODE = 235
    private var imageUri: Uri? = null
    private var categoriesList: List<CategoryEntity>? = null
    private var imagesList: ArrayList<String>? = ArrayList()
    private val complaintsViewModel: ComplaintsViewModel by viewModels()
    private val categoriesAdapter = CategoriesAdapter()
    private var categoryId = 0
    private val listImagesUri: ArrayList<ImageInfo> = ArrayList()
    private val mAdapter = ImagesAdapter()
    override fun getViewBinding() = ActivitySuggestionsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الشكاوي والمقترحات")



        binding.phone.setText(sharedPreferences.phoneVerified)
        binding.phone.isEnabled = false
        complaintsViewModel.getComplaintsCategories()
        complaintsViewModel.categories.observe(this){
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            binding.containerLayout.visibility = View.VISIBLE
                            binding.layout.visibility = View.VISIBLE
                            categoriesList = resource.data
                            categoriesAdapter.submitList(
                                resource.data.toMutableList()
                                    .also { list -> list.add(0, CategoryEntity(0, "اختر الفئة", "")) })
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        complaintsViewModel.complaintStatus.observe(this){
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (!resource.data!!.isNullOrEmpty()){
                            binding.layout.visibility = View.GONE
                            binding.statusText.visibility = View.VISIBLE
                            binding.statusText.text = "لقد تم تسجيل طلبكم بنجاح رقم الشكوي / المقترح ${resource.data}"
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        binding.sendBtn.setOnClickListener {
            addComplaint()
        }

        binding.addImage.setOnClickListener {
            if (listImagesUri.size == 3){
                Toast.makeText(this, "لا يمكن إضافة اكثر من 3 صور.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            checkPermissionsAndOpenFilePicker()
        }

        binding.spCategory.adapter = categoriesAdapter
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (categoriesList != null && i != 0) {
                    categoryId = categoriesList?.get(i - 1)!!.id
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun addComplaint() {

        if (binding.name.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الاسم اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.phone.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الهاتف اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.content.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل محتوي الشكوي اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spCategory.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك إختر الفئة اولا.", Toast.LENGTH_LONG).show()
            return
        }

        for (image in listImagesUri){
            imagesList?.add(getRealPath(image.image)!!.toBase64())
        }

        val complaintBody = ComplaintBody(
            catogory = categoryId,
            name = binding.name.text.toString(),
            details = binding.content.text.toString(),
            documents = imagesList,
            phone = binding.phone.text.toString()
        )

        complaintsViewModel.addComplaint(complaintBody)
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
        if (resultCode == RESULT_OK && data != null) {

            if (REQUEST_CODE == requestCode){
                imageUri = data.data
                listImagesUri.add(ImageInfo(imageUri!!, imageUri?.getName()))
                binding.addImageText.text = "إضافة صورة أخرى."
                binding.imagesRecycler.visibility = View.VISIBLE
                binding.imagesRecycler.adapter = mAdapter
                mAdapter.submitList(listImagesUri)
            }
        }
    }

    private fun Uri.getName(): String {
        val returnCursor = contentResolver.query(this, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor?.moveToFirst()
        val fileName = returnCursor?.getString(nameIndex!!)
        returnCursor?.close()
        return fileName!!
    }

    private fun String.toBase64(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeFile(this)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    private fun getRealPath(uri: Uri): String?{
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor!!.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val yourRealPath = cursor.getString(columnIndex)
            cursor.close()
            return yourRealPath
        }
        return null
    }

}