package com.neqabty.courses.home.presentation.view.coursedetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.courses.R
import com.example.courses.databinding.ActivityCourseDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.FileUtils
import com.neqabty.courses.core.utils.Status
import com.neqabty.courses.home.presentation.model.ErrorUIModel
import com.neqabty.courses.home.presentation.view.homescreen.HomeViewModel
import com.neqabty.courses.offers.presentation.view.COURSEID
import com.neqabty.courses.offers.presentation.view.OffersActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class CourseDetailsActivity : BaseActivity<ActivityCourseDetailsBinding>(), OnMapReadyCallback {

    private var latitude = 30.062768087142633
    private var longitude = 31.245639547705647
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ActivityCourseDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar( title = "تفاصيل الدورة التدريبية")
        val courseId = intent.getIntExtra("courseId", -1)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        homeViewModel.getCourseDetails(courseId.toString())

        homeViewModel.courseDetails.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null){
                            binding.layoutContainer.visibility = View.VISIBLE
                            Picasso.get().load(resource.data[0].image).into(binding.courseImage)
                            binding.courseTitle.setHtmlText(resource.data[0].title)
                            binding.content.setHtmlText( resource.data[0].syllabus)
                            binding.prerequisites.setHtmlText(resource.data[0].prerequisites.toString())
                            binding.whatWeWillLearnValue.setHtmlText(resource.data[0].syllabus)
                            binding.offerBtn.setOnClickListener {
                                startActivity(Intent(this,OffersActivity::class.java).putExtra(COURSEID,resource.data[0].id))
                            }
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
        homeViewModel.reservationStatus.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null){
                            binding.layoutContainer.visibility = View.VISIBLE

                        }else{
                            Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        val error = Gson().fromJson(resource.message, ErrorUIModel::class.java)
                        Toast.makeText(this@CourseDetailsActivity, error.detail, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.bookCourseBtn.setOnClickListener {

            if (imageUrl == null){
                Toast.makeText(this, "من فضلك إختر الصورة.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(this, "من فضلك إدخل رقم الهاتف.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val image: MultipartBody.Part = prepareFilePart("image", imageUrl!!)

            homeViewModel.reservations(
                mobile = binding.phone.text.toString(),
                image = image,
                studentMobile = "+201128236585",
                notes = binding.notes.text.toString(),
                offer = binding.notes.text.toString())
        }

        binding.uploadImage.setOnClickListener {
            checkPermissionsAndOpenFilePicker()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(sydney))
        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.setAllGesturesEnabled(false)

        // For zooming automatically to the location of the marker
        val cameraPosition =
            CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    @NonNull
    private fun createPartFromString(content: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, content)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @NonNull
    private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part {
        val file = FileUtils.getFile(this, fileUri)
        val requestFile = RequestBody.create(
            contentResolver.getType(fileUri).toString().toMediaTypeOrNull(),
            file
        )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
    }

    private val PERMISSIONS_REQUEST_CODE = 15

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissionsAndOpenFilePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    PERMISSIONS_REQUEST_CODE
                )
            }
        } else {
            showChooser()
        }
    }

    private val REQUEST_CODE = 101
    private var imageUrl: Uri? = null

    private fun showChooser() {
        val intent = Intent()
        intent.type = "image/*";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(Intent.createChooser(intent,
            "Select Picture"), REQUEST_CODE);
    }


    private fun showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Log.e("Lionel", data.data.toString() + "")
            imageUrl = data.data
            binding.image.setImageResource(R.drawable.ic_baseline_check_circle_24)
            binding.imageText.setTextColor(resources.getColor(R.color.black))
            binding.imageText.text = "تم إرفاق الصورة بنجاح."
        }
    }
}

fun TextView.setHtmlText(htmlText: String) {
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlText)
    }
}
