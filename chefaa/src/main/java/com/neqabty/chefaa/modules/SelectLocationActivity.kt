package com.neqabty.chefaa.modules

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.LATITUDE
import com.neqabty.chefaa.core.data.Constants.LONGITUDE
import com.neqabty.chefaa.core.data.Constants.latitude
import com.neqabty.chefaa.core.data.Constants.longitude
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ActivitySelectLocationBinding
import com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt


@AndroidEntryPoint
class SelectLocationActivity : BaseActivity<ActivitySelectLocationBinding>(), OnMapReadyCallback {

//    private var latitude = 30.043963618425664
//    private var longitude = 31.234388016164303
    private var district: String? = ""
    private var city: String? = ""
    private var gov: String? = ""
    override fun getViewBinding() = ActivitySelectLocationBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val sydney = LatLng(latitude, longitude)
        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            val midLatLng = googleMap.cameraPosition.target

            latitude = midLatLng.latitude
            longitude = midLatLng.longitude

            getCityName()

        })

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }

    }

    private fun getCityName() {
        if (!binding.select.isVisible){
            binding.address.visibility = View.VISIBLE
            binding.select.visibility = View.VISIBLE
        }
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        try{
            district = addresses[0].locality
            city = addresses[0].subAdminArea
            gov = addresses[0].adminArea
        }catch (e: Exception){

        }
//        val cityName = addresses[0].getAddressLine(0)

        binding.address.text = "${district ?: ""}, ${city ?: ""}, ${gov ?: ""}"
//        findViewById<TextView>(R.id.address).text = "$cityName"

    }

    fun selectLocation(view: View?) {
        val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra(LATITUDE, latitude)
        intent.putExtra(LONGITUDE, longitude)
        intent.putExtra("district", district ?: "")
        intent.putExtra("city", city ?: "")
        intent.putExtra("gov", gov ?: "")
        startActivity(intent)
        finish()
    }

}