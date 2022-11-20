package com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.neqabty.healthcare.core.utils.LocaleHelper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.core.utils.isNationalIdValid
import com.neqabty.healthcare.databinding.ActivitySubscriptionBinding
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.Followers
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.UpdatePackageBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


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
    private var userNumber: String? = ""
    private var subscriptionMode = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.subscription)

        subscriptionMode = intent.getBooleanExtra("subscriptionMode", false)
        binding.ccp.registerCarrierNumberEditText(binding.deliveryPhone)
        name = intent.getStringExtra("name")
        price = intent.getDoubleExtra("price", 0.0)
        serviceCode = intent.getStringExtra("serviceCode")
        maxFollowers = intent.getIntExtra("maxFollowers", 0)
        serviceActionCode = intent.getStringExtra("serviceActionCode")
        userNumber = intent.getStringExtra("userNumber")

        binding.etName.setText(sharedPreferences.name)
        if (!sharedPreferences.nationalId.isNullOrEmpty()){
            binding.etNationalId.setText(sharedPreferences.nationalId)
        }

        binding.etEmail.setText(sharedPreferences.email)
        binding.etEmail.isEnabled = false

        binding.spRelations.adapter = relationsAdapter

        if (!subscriptionMode){
            setupToolbar(title = "تعديل البيانات")
            binding.followersInfo.visibility = View.GONE
        }

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
                            Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.screenContainer.visibility = View.VISIBLE
                    }
                }

            }
        }
        
        subscriptionViewModel.packageStatus.observe(this) {

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
                            Toast.makeText(this, "تم تعديل البيانات بنجاح.", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
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
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE){
                onSelectFromGalleryResult(data!!)
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        if (data != null) {
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, data.data)

                if (bitmap.allocationByteCount / (1024 * 1024) > 25){
                    Toast.makeText(this@SubscriptionActivity, "صورة كبيرة الحجم.", Toast.LENGTH_LONG).show()
                    return
                }
                val photoUI = saveImage(bitmap)

                val file = File(photoUI.path, photoUI.name)

                val res = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
                when (REQUEST_CODE) {
                    1001 -> {
                            userImageUri = res
                            binding.addPersonalPhoto.setImageResource(R.drawable.success)
                            binding.personalPhotoText.text = "تم إرفاق الصورة بنجاح."
                    }
                    1002 -> {
                            nationalIdFrontUri = res
                            binding.addImage.setImageResource(R.drawable.success)
                            binding.addImageText.text = "تم إرفاق الصورة بنجاح."
                    }
                    1003 -> {
                            nationalIdBackUri = res
                            binding.addImageBack.setImageResource(R.drawable.success)
                            binding.addImageTextBack.text = "تم إرفاق الصورة بنجاح."
                    }
                    1004 -> {
                            followerImageUri = data.data!!
                            followerUri = res
                            binding.followerImage.setImageURI(data.data!!)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }}

    private fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 30, bytes)
        val path: String = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = System.currentTimeMillis().toString() + ".jpg"
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
        return PhotoUI(path, name,null)
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

        if (subscriptionMode){

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
        }else{

            val updatePackageBody = UpdatePackageBody(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                birthDate = binding.etBirthDate.text.toString(),
                deliveryPhone = binding.ccp.fullNumberWithPlus,
                address = binding.etAddress.text.toString(),
                job = binding.etJob.text.toString(),
                mobile = binding.etPhone.text.toString(),
                nationalId = binding.etNationalId.text.toString(),
                userNumber = userNumber,
                personalImage = userImageUri!!,
                frontIdImage = nationalIdFrontUri!!,
                backIdImage = nationalIdBackUri!!,
            )

            subscriptionViewModel.updatePackage(updatePackageBody)

        }
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper.setLocale(this, sharedPreferences.language)
    }

}

data class PhotoUI(
    var path: String?,
    var name: String?,
    var uri: Uri?
)