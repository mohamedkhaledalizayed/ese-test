package com.neqabty.chefaa.modules

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants.LATITUDE
import com.neqabty.chefaa.core.data.Constants.LONGITUDE
import com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import java.util.*


class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var latitude = 30.043963618425664
    private var longitude = 31.234388016164303
    var district = ""
    var city = ""
    var gov = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        latitude = intent.getDoubleExtra("LAT",30.043963618425664)
        longitude = intent.getDoubleExtra("LNG",31.234388016164303)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val sydney = LatLng(latitude, longitude)
        googleMap.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            val midLatLng = googleMap.cameraPosition.target
            Log.e(
                "location",
                midLatLng.latitude.toString() + " " + midLatLng.longitude
            )
            latitude = midLatLng.latitude
            longitude = midLatLng.longitude

            getCityName()
        })
        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun getCityName() {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        district = addresses[0].locality
        city = addresses[0].subAdminArea
        gov = addresses[0].adminArea
        val cityName = addresses[0].getAddressLine(0)

        findViewById<TextView>(R.id.address).text = "${district ?: ""}, ${city ?: ""}, ${gov ?: ""}"
//        findViewById<TextView>(R.id.address).text = "$cityName"

    }

    fun selectLocation(view: View?) {
        val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra(LATITUDE, latitude)
        intent.putExtra(LONGITUDE, longitude)
        intent.putExtra("district", district)
        intent.putExtra("city", city)
        intent.putExtra("gov", gov)
        startActivity(intent)
        finish()
    }

}