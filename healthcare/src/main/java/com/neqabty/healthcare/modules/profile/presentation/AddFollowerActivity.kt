package com.neqabty.healthcare.modules.profile.presentation


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivityAddFollowerBinding
import com.neqabty.healthcare.modules.profile.data.model.AddFollowerBody
import com.neqabty.healthcare.modules.profile.data.model.Follower
import com.neqabty.healthcare.modules.profile.domain.entity.relations.RelationEntityList
import com.neqabty.healthcare.modules.profile.presentation.RelationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddFollowerActivity : BaseActivity<ActivityAddFollowerBinding>() {

    private var REQUEST_CODE = 0
    private var followerUri: Uri? = null
    private var nationalIdFrontUri: Uri? = null
    private var nationalIdBackUri: Uri? = null
    private var relationTypeId  = 0
    private lateinit var relation: String
    private var relationsList: List<RelationEntityList>? = null
    private val relationsAdapter = RelationsAdapter()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityAddFollowerBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "إضافة تابع جديد")

        binding.spRelations.adapter = relationsAdapter

        binding.spRelations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (relationsList != null && i != 0) {
                    relationTypeId = relationsList?.get(i - 1)!!.id
                    relation = relationsList?.get(i - 1)!!.relation
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        profileViewModel.getRelations()
        profileViewModel.relations.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isNotEmpty()) {
                            relationsList = resource.data
                            relationsAdapter.submitList(
                                resource.data!!.toMutableList()
                                    .also { list ->
                                        list.add(
                                            0,
                                            RelationEntityList(0, "درجة القرابة")
                                        )
                                    })
                        }
                    }
                    Status.ERROR -> {

                    }
                }
            }
        }

        profileViewModel.addFollower.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->{

                    }
                    Status.SUCCESS ->{

                    }
                    Status.ERROR ->{

                    }
                }

            }
        }

    }

    private fun addFollower(){

        val follower = Follower(
            image = getRealPath(followerUri!!)!!.toBase64(),
            name = "",
            nationalId = 0,
            relationType = 0
        )

        val list = mutableListOf<Follower>()
        list.add(follower)
        profileViewModel.addFollower(
            AddFollowerBody(
            packageId = "",
            subscriberId = "",
            followers = list
        )
        )
    }

    private fun deleteFollower(){
        profileViewModel.deleteFollower(2, "")
    }

    fun addFollowerImage(view: View) {
        REQUEST_CODE = 1004
        checkPermissionsAndOpenFilePicker()
    }
    fun addFollowerNationalIdFrontImage(view: View) {
        REQUEST_CODE = 1005
        checkPermissionsAndOpenFilePicker()
    }
    fun addFollowerNationalIdBackImage(view: View) {
        REQUEST_CODE = 1006
        checkPermissionsAndOpenFilePicker()
    }
    fun addNewFollower(view: View) {}

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

    private fun String.toBase64(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeFile(this)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
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

            when (REQUEST_CODE) {
                1004 -> {
                    followerUri = data.data
                    binding.followerImage.setImageURI(followerUri)
                }
                1005 -> {
                    nationalIdFrontUri = data.data
                    binding.followerAddImage.setImageResource(R.drawable.success)
                    binding.followerAddImageText.text = "تم إرفاق الصورة بنجاح."
                }
                1006 -> {
                    nationalIdBackUri = data.data
                    binding.followerAddImageBack.setImageResource(R.drawable.success)
                    binding.followerAddImageTextBack.text = "تم إرفاق الصورة بنجاح."
                }
            }
        }
    }

}