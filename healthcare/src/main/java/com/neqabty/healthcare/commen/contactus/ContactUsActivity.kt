package com.neqabty.healthcare.commen.contactus

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityContactUsBinding
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

        setupToolbar(titleResId = R.string.contactus_title)

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
            val fm: FragmentManager = supportFragmentManager
            val dialog = NumbersDialog.newInstance(phone)
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
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