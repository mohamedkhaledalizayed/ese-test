package com.neqabty.healthcare.pharmacymart.address.ui.addresseslist


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.presentation.placeprescriptionscreen.CheckOutActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.selectedAddressPharmacyMart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityAddressesBinding
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressEntity
import com.neqabty.healthcare.pharmacymart.address.ui.PharmacyMartAddressViewModel
import com.neqabty.healthcare.pharmacymart.address.ui.addaddress.PharmacyMartAddAddressActivity
import com.neqabty.healthcare.pharmacymart.address.ui.selectlocation.PharmacyMartSelectLocationActivity
import com.neqabty.healthcare.pharmacymart.orders.ui.addorder.AddOrderActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseActivity<ActivityAddressesBinding>(), LocationListener {

    private lateinit var locationManager: LocationManager
    private val mAdapter = AddressAdapter()
    private val viewModel: PharmacyMartAddressViewModel by viewModels()
    override fun getViewBinding() = ActivityAddressesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }catch (e: Exception){

        }


        viewModel.addresses.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressActivity.showLoading()
                }
                Status.SUCCESS ->{
                    if (it.data!!.data.isEmpty()) {
                        binding.progressActivity.showEmpty(
                            R.drawable.ic_no_data_found,
                            "لا يوجد عناوين",
                            "برجاء إضافة عنوان"
                        )
                    }else {
                        binding.progressActivity.showContent()
                        mAdapter.submitList(it.data.data)
                    }
                }
                Status.ERROR ->{
                    Log.e("hh", it.message.toString())
                    binding.progressActivity.showEmpty(
                        R.drawable.ic_no_data_found,
                        "خطا",
                        it.message
                    )
                }
            }
        }

        binding.addressRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(addressItem: PharmacyMartAddressEntity) {
                if (!checkGPS()){
                    buildAlertMessageNoGps()
                    return
                }
                selectedAddressPharmacyMart = addressItem
                startActivity(Intent(this@AddressesActivity, AddOrderActivity::class.java))
                finish()
            }
        }

        binding.addAddressBtn.setOnClickListener {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions()
                return@setOnClickListener
            }

            if (!checkGPS()){
                buildAlertMessageNoGps()
                return@setOnClickListener
            }

            startActivity(Intent(this@AddressesActivity, PharmacyMartSelectLocationActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddresses()
    }

    override fun onLocationChanged(location: Location) {
        Constants.latitude = location.latitude
        Constants.longitude = location.longitude
    }

}