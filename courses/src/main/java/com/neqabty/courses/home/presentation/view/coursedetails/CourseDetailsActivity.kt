package com.neqabty.courses.home.presentation.view.coursedetails


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.courses.R
import com.example.courses.databinding.ActivityCourseDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.neqabty.courses.core.data.Constants.COURSEID
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.Status
import com.neqabty.courses.core.utils.setHtmlText
import com.neqabty.courses.home.presentation.view.homescreen.HomeViewModel
import com.neqabty.courses.offers.presentation.view.OffersActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CourseDetailsActivity : BaseActivity<ActivityCourseDetailsBinding>(), OnMapReadyCallback {

    private var latitude = 30.062768087142633
    private var longitude = 31.245639547705647
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ActivityCourseDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "تفاصيل الدورة التدريبية")
        val courseId = intent.getIntExtra("courseId", -1)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        homeViewModel.getCourseDetails(courseId.toString())

        homeViewModel.courseDetails.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null) {
                            binding.layoutContainer.visibility = View.VISIBLE
                            Picasso.get().load(resource.data[0].image).into(binding.courseImage)
                            binding.courseTitle.setHtmlText(resource.data[0].title)
                            binding.content.setHtmlText(resource.data[0].syllabus)
                            binding.prerequisites.setHtmlText(resource.data[0].prerequisites.toString())
                            binding.whatWeWillLearnValue.setHtmlText(resource.data[0].syllabus)
                            binding.offerBtn.setOnClickListener {
                                startActivity(
                                    Intent(this, OffersActivity::class.java).putExtra(
                                        COURSEID,
                                        resource.data[0].id
                                    )
                                )
                            }
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
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


}
