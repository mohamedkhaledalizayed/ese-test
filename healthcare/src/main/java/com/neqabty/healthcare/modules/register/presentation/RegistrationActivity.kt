package com.neqabty.healthcare.modules.register.presentation


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityRegistrationBinding
import com.neqabty.healthcare.modules.register.presentation.model.Follower


class RegistrationActivity : BaseActivity<ActivityRegistrationBinding>() {

    private var REQUEST_CODE = 1000
    private val mAdapter = FollowerAdapter()
    private var listFollower = mutableListOf<Follower>()
    private lateinit var relation: String
    private var imageUrl: Uri? = null
    override fun getViewBinding() = ActivityRegistrationBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تسجيل إشتراك")



        mAdapter.onItemClickListener = object :
            FollowerAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        binding.spRelations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                relation = binding.spRelations.getItemAtPosition(i).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

    }

    fun addFollower(view: View) {
        binding.addFollower.visibility = View.GONE
        binding.addFollowerText.visibility = View.GONE
        binding.followerInfo.visibility = View.VISIBLE
    }

    fun addNewFollower(view: View) {

        if (imageUrl == null || REQUEST_CODE != 1001){
            Toast.makeText(this, "من فضلك اختر صورة اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etFullName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الاسم اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNational.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى اولا.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spRelations.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر درجة القرابة اولا.", Toast.LENGTH_LONG).show()
            return
        }

        val follower = Follower(
            id = 1,
            name = binding.etFullName.text.toString(),
            image = imageUrl!!,
            nationalId = binding.etNational.text.toString(),
            relation = relation
        )

        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)

        binding.etFullName.setText("")
        binding.etNational.setText("")
        binding.spRelations.setSelection(0)
        binding.followerImage.setImageResource(R.drawable.user)
        binding.followerInfo.visibility = View.GONE
        binding.addFollower.visibility = View.VISIBLE
        binding.addFollowerText.visibility = View.VISIBLE
        binding.followersRecycler.visibility = View.VISIBLE


    }

    fun addFollowerImage(view: View) {
        REQUEST_CODE = 1001
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
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            imageUrl = data.data
            when (REQUEST_CODE) {
                1001 -> {
                    binding.followerImage.setImageURI(imageUrl)
                }
                1002 -> {
                    binding.addImage.setImageResource(R.drawable.success)
                    binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                }
                1003 -> {
                    binding.userPicture.setImageURI(imageUrl)
                }
                else -> {
                    imageUrl = null
                }
            }
        }
    }

    fun addImage(view: View) {
        REQUEST_CODE = 1002
        checkPermissionsAndOpenFilePicker()
    }

    fun changeUserPicture(view: View) {
        REQUEST_CODE = 1003
        checkPermissionsAndOpenFilePicker()
    }
}