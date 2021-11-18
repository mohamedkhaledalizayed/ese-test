package com.neqabty.yodawy.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.LONGITUDE
import com.neqabty.yodawy.core.data.Constants.LATITUDE
import com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen.AddAddressActivity

class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var latitude = 30.062768087142633
    private var longitude = 31.245639547705647
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

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
        })
        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun selectLocation(view: View?) {
        val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra(LATITUDE, latitude)
        intent.putExtra(LONGITUDE, longitude)
        startActivity(intent)
        finish()
    }

}