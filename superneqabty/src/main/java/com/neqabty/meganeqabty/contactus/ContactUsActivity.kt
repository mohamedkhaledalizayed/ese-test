package com.neqabty.meganeqabty.contactus

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.neqabty.core.data.Constants
import com.neqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.ActivityContactUsBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ContactUsActivity : BaseActivity<ActivityContactUsBinding>(), OnMapReadyCallback {

    private var latitude = 30.0570044
    private var longitude = 31.2419151
    private var phone = Constants.TOUR_GUIDE_SYNDICATE_PHONE
    private var email = Constants.TOUR_GUIDE_SYNDICATE_EMAIL
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = ActivityContactUsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "اتصل بنا")

        if (intent.getIntExtra("key", 0) == 101){
            latitude = Constants.NEQABTY_LAT
            longitude = Constants.NEQABTY_LONG
            phone = Constants.NEQABTY_PHONE
            email = Constants.NEQABTY_EMAIL
            binding.location.text = Constants.NEQABTY_ADDRESS
            binding.phoneNumber.text = Constants.NEQABTY_PHONE
            binding.emailAddress.text = Constants.NEQABTY_EMAIL
        }

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        binding.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            try {
                startActivity(intent)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        binding.emailLayout.setOnClickListener {
            try {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                startActivity(emailIntent)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        binding.location.setOnClickListener {
            location()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(sydney))
        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.setAllGesturesEnabled(false)

        val cameraPosition =
            CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun location() {
        val navigationIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}