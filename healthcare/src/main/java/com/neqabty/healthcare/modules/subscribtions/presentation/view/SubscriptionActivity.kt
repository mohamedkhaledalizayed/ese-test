package com.neqabty.healthcare.modules.subscribtions.presentation.view


import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySubscriptionBinding
import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import com.neqabty.healthcare.modules.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.modules.subscribtions.presentation.viewmodel.SubscriptionViewModel
import com.neqabty.signup.core.utils.isNationalIdValid
import com.neqabty.signup.core.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*


@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding>() {
    private var REQUEST_CODE = 0
    private val mAdapter = FollowerAdapter()
    private var listFollower = mutableListOf<Followers>()
    private lateinit var relation: String
    private var userImageUri: Uri? = null
    private var nationalIdFrontUri: Uri? = null
    private var nationalIdBackUri: Uri? = null
    private var followerUri: Uri? = null
    private val relationsAdapter = RelationsAdapter()
    private var relationsList: List<RelationEntity>? = null
    private var relationTypeId  = 0
    private lateinit var datePicker: DatePickerDialog
    private lateinit var calendar: Calendar
    override fun getViewBinding() = ActivitySubscriptionBinding.inflate(layoutInflater)
    private val subscriptionViewModel: SubscriptionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تسجيل إشتراك")

        binding.spRelations.adapter = relationsAdapter
        binding.spRelations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (relationsList != null && i != 0) {
                    relationTypeId = relationsList?.get(i - 1)!!.id
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        calendar = Calendar.getInstance()
        binding.etPhone.setText(sharedPreferences.phoneVerified)
        binding.etPhone.isEnabled = false
        binding.etBirthDate.isEnabled = false

        binding.selectDate.setOnClickListener {
            datePicker = DatePickerDialog(this@SubscriptionActivity,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.etBirthDate.setText("$dayOfMonth - ${monthOfYear + 1} - $year")
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        subscriptionViewModel.getRelations()
        subscriptionViewModel.relations.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isNotEmpty()){
                            relationsList = resource.data
                            relationsAdapter.submitList(
                                resource.data.toMutableList()
                                    .also { list -> list.add(0, RelationEntity(0, "درجة القرابة")) })
                        }
                    }
                    Status.ERROR -> {

                    }
                }
            }
        }
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

        subscriptionViewModel.providers.observe(this) {

            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.screenContainer.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.screenContainer.visibility = View.VISIBLE
                        if (resource.data!!){
                            finish()
                            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                        }
                        Log.e("SUCCESS", "${resource.data}")
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.screenContainer.visibility = View.VISIBLE
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }
    }

    fun addFollower(view: View) {
        binding.addFollower.visibility = View.GONE
        binding.addFollowerText.visibility = View.GONE
        binding.followerInfo.visibility = View.VISIBLE
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

        if (binding.etNational.text.toString().isNationalIdValid()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNationalId.text.toString().length < 14){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spRelations.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر درجة القرابة.", Toast.LENGTH_LONG).show()
            return
        }

        val follower = Followers(
            name = binding.etFullName.text.toString(),
            image = getRealPath(followerUri!!)!!.toBase64(),
            national_id = binding.etNational.text.toString(),
            relation_type = relationTypeId
        )

        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)

        followerUri = null
        relationTypeId = 0
        binding.etFullName.setText("")
        binding.etNational.setText("")
        binding.spRelations.setSelection(0)
        binding.followerImage.setImageResource(R.drawable.user)
        binding.followerInfo.visibility = View.GONE
        binding.addFollower.visibility = View.VISIBLE
        binding.addFollowerText.visibility = View.VISIBLE
        binding.followersRecycler.visibility = View.VISIBLE


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
                1001 -> {
                    userImageUri = data.data
                    binding.userPicture.setImageURI(userImageUri)
                }
                1002 -> {
                    nationalIdFrontUri = data.data
                    binding.addImage.setImageResource(R.drawable.success)
                    binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                }
                1003 -> {
                    nationalIdBackUri = data.data
                    binding.addImageBack.setImageResource(R.drawable.success)
                    binding.addImageTextBack.text = "تم إرفاق الصورة بنجاح."
                }
                1004 -> {
                    followerUri = data.data
                    binding.followerImage.setImageURI(followerUri)
                }
            }
        }
    }

    fun changeUserPicture(view: View) {
        REQUEST_CODE = 1001
        checkPermissionsAndOpenFilePicker()
    }

    fun addNationalIdFrontImage(view: View) {
        REQUEST_CODE = 1002
        checkPermissionsAndOpenFilePicker()
    }

    fun addNationalIdBackImage(view: View) {
        REQUEST_CODE = 1003
        checkPermissionsAndOpenFilePicker()
    }

    fun addFollowerImage(view: View) {
        REQUEST_CODE = 1004
        checkPermissionsAndOpenFilePicker()
    }

    fun registerUser(view: View) {

        if (userImageUri == null){
            Toast.makeText(this, "من فضلك اختر الصورة الشخصية.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الاسم.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etBirthDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل تاريخ الميلاد.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etAddress.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل العنوان.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etJob.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الوظيفة.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNationalId.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNationalId.text.toString().isNationalIdValid()){
            Toast.makeText(this, "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etNationalId.text.toString().length < 14){
            Toast.makeText(this, "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etEmail.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل البريد الالكترونى.", Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.etEmail.text.toString().isValidEmail()){
            Toast.makeText(this, "من فضلك ادخل البريد الالكترونى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (nationalIdFrontUri == null){
            Toast.makeText(this, "من فضلك اختر صورة البطاقة الامامية.", Toast.LENGTH_LONG).show()
            return
        }

        if (nationalIdBackUri == null){
            Toast.makeText(this, "من فضلك اختر صورة البطاقة الخلفية.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etPhone.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل الهاتف.", Toast.LENGTH_LONG).show()
            return
        }

        subscriptionViewModel.addSubscription(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            birthDate = binding.etBirthDate.text.toString(),
            address = binding.etAddress.text.toString(),
            job = binding.etJob.text.toString(),
            mobile = binding.etPhone.text.toString(),
            nationalId = binding.etNationalId.text.toString(),
            syndicateId = 1,
            packageId = "efd2dde7-51ea-40ef-95b1-663d8b310754",
            referralNumber = "",
            fronIdImage = getRealPath(nationalIdFrontUri!!)!!.toBase64(),
            backIdImage = getRealPath(nationalIdBackUri!!)!!.toBase64(),
            personalImage = getRealPath(userImageUri!!)!!.toBase64(),
            followers = listFollower
        )
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