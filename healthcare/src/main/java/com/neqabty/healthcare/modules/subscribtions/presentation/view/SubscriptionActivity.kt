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
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySubscriptionBinding
import com.neqabty.healthcare.modules.payment.view.SehaPaymentActivity
import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import com.neqabty.healthcare.modules.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.modules.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.modules.subscribtions.presentation.viewmodel.SubscriptionViewModel
import com.neqabty.signup.core.utils.isMobileValid
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
    private var followerNationalIdFrontUri: Uri? = null
    private var followerNationalIdBackUri: Uri? = null
    private var followerUri: Uri? = null
    private val relationsAdapter = RelationsAdapter()
    private var relationsList: List<RelationEntity>? = null
    private var relationTypeId  = 0
    private lateinit var datePicker: DatePickerDialog
    private lateinit var calendar: Calendar
    override fun getViewBinding() = ActivitySubscriptionBinding.inflate(layoutInflater)
    private val subscriptionViewModel: SubscriptionViewModel by viewModels()
    private var name: String? = ""
    private var price: Double? = 0.0
    private var serviceCode: String? = ""
    private var serviceActionCode: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تسجيل إشتراك")

        name = intent.getStringExtra("name")
        price = intent.getDoubleExtra("price", 0.0)
        serviceCode = intent.getStringExtra("serviceCode")
        serviceActionCode = intent.getStringExtra("serviceActionCode")
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

        calendar = Calendar.getInstance()
        binding.etPhone.setText(sharedPreferences.mobile)
        binding.etBirthDate.isEnabled = false

        binding.selectDate.setOnClickListener {
            datePicker = DatePickerDialog(
                this@SubscriptionActivity,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.etBirthDate.setText("$year-${monthOfYear + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        subscriptionViewModel.getRelations()
        subscriptionViewModel.relations.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isNotEmpty()) {
                            relationsList = resource.data
                            relationsAdapter.submitList(
                                resource.data.toMutableList()
                                    .also { list ->
                                        list.add(
                                            0,
                                            RelationEntity(0, "درجة القرابة")
                                        )
                                    })
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
                            Toast.makeText(this, "تم الأشتراك بنجاح.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, SehaPaymentActivity::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("price", price)
                            intent.putExtra("serviceCode", serviceCode)
                            intent.putExtra("serviceActionCode", serviceActionCode)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, "حدث خطا ما, برجاء مراجعة البيانات مرة أخرى.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.screenContainer.visibility = View.VISIBLE
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

        if (followerNationalIdFrontUri == null){
            Toast.makeText(this, "من فضلك اختر صورة البطاقة الامامية.", Toast.LENGTH_LONG).show()
            return
        }

        if (followerNationalIdBackUri == null){
            Toast.makeText(this, "من فضلك اختر صورة البطاقة الخلفية.", Toast.LENGTH_LONG).show()
            return
        }

        val follower = Followers(
            name = binding.etFullName.text.toString(),
            relation = relation,
            imageUri = followerUri!!,
            national_id = binding.etNational.text.toString(),
            relation_type = relationTypeId,
            image = getRealPath(followerUri!!)!!.toBase64(),
            frontIdImage = getRealPath(followerNationalIdFrontUri!!)!!.toBase64(),
            backIdImage = getRealPath(followerNationalIdBackUri!!)!!.toBase64()
        )

        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)

        followerUri = null
        followerNationalIdFrontUri = null
        followerNationalIdBackUri = null
        binding.etFullName.setText("")
        binding.etNational.setText("")
        binding.spRelations.setSelection(0)
        binding.followerImage.setImageResource(R.drawable.user)
        binding.followerInfo.visibility = View.GONE
        binding.followerAddImage.setImageResource(R.drawable.ic_baseline_attach_file_24)
        binding.followerAddImageText.text = "إرفاق صورة البطاقة الامامية"
        binding.followerAddImageBack.setImageResource(R.drawable.ic_baseline_attach_file_24)
        binding.followerAddImageTextBack.text = "إرفاق صورة البطاقة الخلفية"
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
                1005 -> {
                    followerNationalIdFrontUri = data.data
                    binding.followerAddImage.setImageResource(R.drawable.success)
                    binding.followerAddImageText.text = "تم إرفاق الصورة بنجاح."
                }
                1006 -> {
                    followerNationalIdBackUri = data.data
                    binding.followerAddImageBack.setImageResource(R.drawable.success)
                    binding.followerAddImageTextBack.text = "تم إرفاق الصورة بنجاح."
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

    fun addFollowerNationalIdFrontImage(view: View) {
        REQUEST_CODE = 1005
        checkPermissionsAndOpenFilePicker()
    }

    fun addFollowerNationalIdBackImage(view: View) {
        REQUEST_CODE = 1006
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

        if (!binding.etNationalId.text.toString().isNationalIdValid()){
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

        if (!binding.etReferralNumber.text.toString().isNullOrEmpty() && !binding.etReferralNumber.text.toString().isMobileValid()){
            Toast.makeText(this, "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        val sub = SubscribePostBodyRequest(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            birthDate = binding.etBirthDate.text.toString(),
            address = binding.etAddress.text.toString(),
            job = binding.etJob.text.toString(),
            mobile = binding.etPhone.text.toString(),
            nationalId = binding.etNationalId.text.toString(),
            entityCode = Constants.NEQABTY_CODE,
            serviceActionCode = "$serviceActionCode",
            referralNumber = binding.etReferralNumber.text.toString(),
            personalImage = getRealPath(userImageUri!!)!!.toBase64(),
            frontIdImage = getRealPath(nationalIdFrontUri!!)!!.toBase64(),
            backIdImage = getRealPath(nationalIdBackUri!!)!!.toBase64(),
            followers = listFollower
        )

        subscriptionViewModel.addSubscription(sub)
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