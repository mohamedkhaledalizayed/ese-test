package com.neqabty.healthcare.pharmacymart.address.ui.selectlocation



import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPharmacyMartSelectLocationBinding
import com.neqabty.healthcare.pharmacymart.address.ui.addaddress.PharmacyMartAddAddressActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class PharmacyMartSelectLocationActivity : BaseActivity<ActivityPharmacyMartSelectLocationBinding>(),
    OnMapReadyCallback {

    private var gov: String? = ""
    private var city: String? = ""
    private var district: String? = ""
    override fun getViewBinding() = ActivityPharmacyMartSelectLocationBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val sydney = LatLng(Constants.latitude, Constants.longitude)
        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            val midLatLng = googleMap.cameraPosition.target

            Constants.latitude = midLatLng.latitude
            Constants.longitude = midLatLng.longitude

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
        val addresses: List<Address> = geocoder.getFromLocation(
            Constants.latitude,
            Constants.longitude, 1) as List<Address>
        try{
            district = addresses[0].locality
            city = addresses[0].subAdminArea
            gov = addresses[0].adminArea
        }catch (e: Exception){

        }

        binding.address.text = "${district ?: ""}, ${city ?: ""}, ${gov ?: ""}"

    }

    fun selectLocation(view: View?) {
        val intent = Intent(this, PharmacyMartAddAddressActivity::class.java)
        intent.putExtra(Constants.LATITUDE, Constants.latitude)
        intent.putExtra(Constants.LONGITUDE, Constants.longitude)
        intent.putExtra("district", district ?: "")
        intent.putExtra("city", city ?: "")
        intent.putExtra("gov", gov ?: "")
        startActivity(intent)
        finish()
    }

}