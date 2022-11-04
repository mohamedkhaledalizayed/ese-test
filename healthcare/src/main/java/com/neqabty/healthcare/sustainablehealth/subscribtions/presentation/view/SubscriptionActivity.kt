package com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view


import android.Manifest
import android.app.Activity
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.databinding.ActivitySubscriptionBinding
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.Followers
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*
import androidx.activity.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.*

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding>() {
    private var REQUEST_CODE = 0
    private val mAdapter = FollowerAdapter()
    private var listFollower = mutableListOf<Followers>()
    private lateinit var relation: String
    private var userImageUri: String? = null
    private var nationalIdFrontUri: String? = null
    private var nationalIdBackUri: String? = null
    private var followerUri: String? = null
    private var followerImageUri: Uri? = null
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
    private var maxFollowers: Int = 0
    private var serviceActionCode: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.subscription)

        binding.ccp.registerCarrierNumberEditText(binding.deliveryPhone)
        name = intent.getStringExtra("name")
        price = intent.getDoubleExtra("price", 0.0)
        serviceCode = intent.getStringExtra("serviceCode")
        maxFollowers = intent.getIntExtra("maxFollowers", 0)
        serviceActionCode = intent.getStringExtra("serviceActionCode")

        binding.etName.setText(sharedPreferences.name)
        binding.etName.isEnabled = false
        if (!sharedPreferences.nationalId.isNullOrEmpty()){
            binding.etNationalId.setText(sharedPreferences.nationalId)
            binding.etNationalId.isEnabled = false
        }

        binding.etEmail.setText(sharedPreferences.email)
        binding.etEmail.isEnabled = false

        binding.spRelations.adapter = relationsAdapter
        if (maxFollowers == 0){
            binding.followersInfo.visibility = View.GONE
        }
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
        binding.etPhone.isEnabled = false
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
                                resource.data!!.toMutableList()
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
                        if (resource.data!!.status){
                            Toast.makeText(this, "تم الأشتراك بنجاح.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, SehaPaymentActivity::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("price", price)
                            intent.putExtra("serviceCode", serviceCode)
                            intent.putExtra("serviceActionCode", serviceActionCode)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, resource.data!!.message, Toast.LENGTH_LONG).show()
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
        if (listFollower.size >= maxFollowers){
            Toast.makeText(this, "لا يمكن إضافة أكثر من $maxFollowers تابعين ", Toast.LENGTH_LONG).show()
            return
        }
        binding.addFollower.visibility = View.GONE
        binding.addFollowerText.visibility = View.GONE
        binding.followerInfo.visibility = View.VISIBLE
    }

    fun addNewFollower(view: View) {

        if (followerImageUri == null){
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

        val follower = Followers(
            name = binding.etFullName.text.toString(),
            relation = relation,
            imageUri = followerImageUri!!,
            national_id = binding.etNational.text.toString(),
            relation_type = relationTypeId,
            image = followerUri!!
        )

        listFollower.add(follower)
        binding.followersRecycler.adapter = mAdapter
        mAdapter.submitList(listFollower)

        followerUri = null
        followerImageUri = null
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
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_PICK
//        startActivityForResult(
//            Intent.createChooser(intent,
//                "Select Picture"), REQUEST_CODE)

        ImagePicker.with(this)
            .galleryOnly()
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
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
        if (resultCode == RESULT_OK && data != null) {

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val uri: Uri = data?.data!!


                    when (REQUEST_CODE) {
                        1001 -> {
                            userImageUri = uri.path?.toBase64()
                            binding.addPersonalPhoto.setImageResource(R.drawable.success)
                            binding.personalPhotoText.text = "تم إرفاق الصورة بنجاح."
                        }
                        1002 -> {
                            nationalIdFrontUri = uri.path?.toBase64()
                            binding.addImage.setImageResource(R.drawable.success)
                            binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                        }
                        1003 -> {
                            nationalIdBackUri = uri.path?.toBase64()
                            binding.addImageBack.setImageResource(R.drawable.success)
                            binding.addImageTextBack.text = "تم إرفاق الصورة بنجاح."
                        }
                        1004 -> {
                            followerImageUri = uri
                            followerUri = uri.path?.toBase64()
                            binding.followerImage.setImageURI(uri)
                        }
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
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

        if (binding.etEmail.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل البريد الالكترونى.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etBirthDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل تاريخ الميلاد.", Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.ccp.isValidFullNumber) {
            Toast.makeText(this, "Not Valid Number", Toast.LENGTH_LONG).show()
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

//        if (!binding.etReferralNumber.text.toString().isNullOrEmpty() && !binding.etReferralNumber.text.toString().isMobileValid()){
//            Toast.makeText(this, "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
//            return
//        }

        val sub = SubscribePostBodyRequest(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            birthDate = binding.etBirthDate.text.toString(),
            deliveryPhone = binding.ccp.fullNumberWithPlus,
            address = binding.etAddress.text.toString(),
            job = binding.etJob.text.toString(),
            mobile = binding.etPhone.text.toString(),
            nationalId = binding.etNationalId.text.toString(),
            entityCode = Constants.NEQABTY_CODE,
            serviceActionCode = "$serviceActionCode",
            referralNumber = binding.etReferralNumber.text.toString(),
            personalImage = userImageUri!!,
            frontIdImage = nationalIdFrontUri!!,
            backIdImage = nationalIdBackUri!!,
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

    override fun onStart() {
        super.onStart()
        LocaleHelper.setLocale(this, sharedPreferences.language)
    }

}