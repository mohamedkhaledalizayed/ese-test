package com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen

import android.app.AlertDialog
import android.content.Intent
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
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.LONGITUDE
import com.neqabty.yodawy.core.data.Constants.LATITUDE
import com.neqabty.yodawy.databinding.ActivityAddAddressBinding
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.modules.SelectLocationActivity
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.address.domain.params.EditAddressUseCaseParams
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddAddressActivity : BaseActivity<ActivityAddAddressBinding>(), OnMapReadyCallback {
    private val addAddressViewModel: AddAddressViewModel by viewModels()

    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0
    private var address: AddressEntity? = null
    private lateinit var dialog: AlertDialog
override fun getViewBinding() = ActivityAddAddressBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.add_new_address)

        latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        longitude = intent.getDoubleExtra(LONGITUDE, 0.0)
        address = intent.extras?.getParcelable<AddressEntity>("address")


        binding.editLocation.setOnClickListener {
            val intent: Intent = Intent(this, SelectLocationActivity::class.java)
            intent.putExtra("isEditable", true)
            startActivityForResult(intent, 201)
        }
        if (!address?.address.isNullOrEmpty()){
            binding.street.setText(address?.address)
            binding.building.setText(address?.buildingNumber)
            binding.apartment.setText(address?.apt)
            binding.floor.setText(address?.floor)
            binding.landmark.setText(address?.landmark)
            binding.nickname.setText(address?.addressName)
            latitude = address?.latitude
            longitude = address?.longitude
            binding.saveBtn.text = "تعديل"
        }

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveAddress(view: View) {
        if (binding.street.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.add_street_name), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.building.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.add_building_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.floor.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.add_floor_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.apartment.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.add_apartment_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.nearest_landmark), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isBlank()){
            Toast.makeText(this, getString(R.string.nickname_), Toast.LENGTH_LONG).show()
            return
        }

        if (latitude == 0.0 || longitude == 0.0 ){
            Toast.makeText(this, getString(R.string.location), Toast.LENGTH_LONG).show()
            return
        }

        if (!address?.address.isNullOrEmpty()){
            addAddressViewModel.editAddress(
                EditAddressUseCaseParams(address?.adressId!!,
                Constants.mobileNumber,
                binding.nickname.text.toString(),
                binding.street.text.toString(),
                binding.floor.text.toString(),
                binding.building.text.toString(),
                binding.apartment.text.toString(),
                longitude!!,
                latitude!!,
                binding.landmark.text.toString())
            )
            addAddressViewModel.editData.observe(this){

                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            dialog.show()
                        }
                        Status.SUCCESS -> {
                            dialog.dismiss()
                            if (!resource.data.isNullOrEmpty()){
                                finish()
                            }
                        }
                        Status.ERROR -> {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }else {

            addAddressViewModel.addAddress(
                AddAddressUseCaseParams(
                    Constants.mobileNumber,
                    binding.nickname.text.toString(),
                    binding.street.text.toString(),
                    binding.floor.text.toString(),
                    binding.building.text.toString(),
                    binding.apartment.text.toString(),
                    "$latitude",
                    "$longitude",
                    binding.landmark.text.toString()
                )
            )
            addAddressViewModel.data.observe(this) {

                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            dialog.show()
                        }
                        Status.SUCCESS -> {
                            dialog.dismiss()
                            if (!resource.data.isNullOrEmpty()) {
                                finish()
                            }
                        }
                        Status.ERROR -> {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    lateinit var map: GoogleMap
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val sydney = LatLng(latitude!!, longitude!!)
        googleMap.addMarker(MarkerOptions().position(sydney))
        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.setAllGesturesEnabled(false)

        // For zooming automatically to the location of the marker
        val cameraPosition =
            CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            latitude = data.getDoubleExtra(LATITUDE, 0.0)
            longitude = data.getDoubleExtra(LONGITUDE, 0.0)
            onMapReady(map)
        }
    }
}