package com.neqabty.healthcare.mypackages.addfollower.view


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
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.core.utils.isNationalIdValid
import com.neqabty.healthcare.databinding.ActivityAddFollowerBinding
import com.neqabty.healthcare.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.mypackages.addfollower.data.model.Follower
import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddFollowerActivity : BaseActivity<ActivityAddFollowerBinding>() {

    private var REQUEST_CODE = 0
    private var followerUri: Uri? = null
    private var relationTypeId  = 0
    private lateinit var relation: String
    private var relationsList: List<RelationEntityList>? = null
    private val relationsAdapter = RelationsAdapter()
    private val addFollowerViewModel: AddFollowerViewModel by viewModels()
    override fun getViewBinding() = ActivityAddFollowerBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.add_new_member)

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

        addFollowerViewModel.getRelations()
        addFollowerViewModel.relations.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.container.visibility = View.VISIBLE
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
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }

        addFollowerViewModel.addFollower.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->{
                        binding.container.visibility = View.GONE
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        binding.container.visibility = View.VISIBLE

                        if (resource.data!!.status){
                            Toast.makeText(this@AddFollowerActivity, "تم إضافة التابع بنجاح.", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this@AddFollowerActivity, resource.data!!.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                        binding.container.visibility = View.VISIBLE
                        Toast.makeText(this@AddFollowerActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }

    private fun addFollower(){

        val follower = Follower(
            image = getRealPath(followerUri!!)!!.toBase64(),
            name = binding.etFullName.text.toString(),
            nationalId = binding.etNational.text.toString(),
            relationType = relationTypeId
        )

        val list = mutableListOf<Follower>()
        list.add(follower)
        addFollowerViewModel.addFollower(
            AddFollowerBody(
            packageId = intent.getStringExtra("packageId")!!,
            subscriberId = intent.getStringExtra("subscriberId")!!, mobile = sharedPreferences.mobile,
            followers = list
        )
        )
    }

    fun addFollowerImage(view: View) {
        REQUEST_CODE = 1004
        checkPermissionsAndOpenFilePicker()
    }
    fun addNewFollower(view: View) {


        if (followerUri == null){
            Toast.makeText(this, "من فضلك اختر صورة.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etFullName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الاسم.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNational.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى.", Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.etNational.text.toString().isNationalIdValid()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNational.text.toString().length < 14){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spRelations.selectedItemPosition == 0 || relationTypeId == 0){
            Toast.makeText(this, "من فضلك اختر درجة القرابة.", Toast.LENGTH_LONG).show()
            return
        }

        addFollower()
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
                    binding.addIcon.visibility = View.GONE
                    binding.addText.visibility = View.GONE
                }
            }
        }
    }

}