package com.neqabty.chefaa.modules.address.presentation.view.addaddressscreen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.LONGITUDE
import com.neqabty.chefaa.core.data.Constants.LATITUDE
import com.neqabty.chefaa.databinding.CehfaaActivityAddAddressBinding
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddAddressActivity : BaseActivity<CehfaaActivityAddAddressBinding>(), OnMapReadyCallback {
    private val addAddressViewModel: AddAddressViewModel by viewModels()

    private var latitude = 0.0
    private var longitude = 0.0
    var district = ""
    var city = ""
    var gov = ""
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = CehfaaActivityAddAddressBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.add_new_address)

        latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        longitude = intent.getDoubleExtra(LONGITUDE, 0.0)
        district = intent.getStringExtra("district")!!
        city = intent.getStringExtra("city")!!
        gov = intent.getStringExtra("gov")!!
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        binding.street.setText("${district ?: ""}, ${city ?: ""}, ${gov ?: ""}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveAddress(view: View) {
        if (binding.street.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_street_name), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.building.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_building_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.floor.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_floor_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.apartment.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_apartment_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.nearest_landmark), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.nickname_), Toast.LENGTH_LONG).show()
            return
        }

        if (latitude == 0.0 || longitude == 0.0 ){
            Toast.makeText(this, getString(R.string.location), Toast.LENGTH_LONG).show()
            return
        }


        addAddressViewModel.addAddress(
            phone = Constants.mobileNumber,
            title = binding.nickname.text.toString(),
            streetName= binding.street.text.toString(),
            floor = binding.floor.text.toString(),
            buildingNo = binding.building.text.toString(),
            apartment = binding.apartment.text.toString(),
            lat = latitude.toString() ,
            long = longitude.toString(),
            landMark =binding.landmark.text.toString())


        addAddressViewModel.data.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING->{
                        dialog.show()
                    }
                    Status.SUCCESS->{
                        Toast.makeText(this, "تم إضافة العنوان بنجاح.", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        finish()
                    }
                    Status.ERROR->{
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        dialog.dismiss()
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