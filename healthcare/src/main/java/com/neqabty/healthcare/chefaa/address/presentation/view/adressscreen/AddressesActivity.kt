package com.neqabty.healthcare.chefaa.address.presentation.view.adressscreen


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.address.presentation.view.selectlocation.SelectLocationActivity
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.orders.presentation.placeprescriptionscreen.CheckOutActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.CehfaaActivityAddressesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseActivity<CehfaaActivityAddressesBinding>(), LocationListener {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()


    private lateinit var locationManager: LocationManager
    override fun getViewBinding() = CehfaaActivityAddressesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }catch (e: Exception){

        }
        addressViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isEmpty()) {
                            binding.progressActivity.showEmpty(
                                R.drawable.ic_no_data_found,
                                "لا يوجد عناوين",
                                "برجاء إضافة عنوان"
                            )
                        } else {
                            binding.progressActivity.showContent()
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressActivity.showEmpty(
                            R.drawable.ic_no_data_found,
                            "خطا",
                            resource.message
                        )
                    }
                }
            }

        }
        binding.addressRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(addressItem: AddressEntity) {
                Constants.selectedAddress = addressItem
                startActivity(Intent(this@AddressesActivity, CheckOutActivity::class.java))
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

            val intent = Intent(this@AddressesActivity, SelectLocationActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        addressViewModel.getUser(Constants.userNumber, Constants.mobileNumber)
    }

    override fun onLocationChanged(location: Location) {
        Constants.latitude = location.latitude
        Constants.longitude = location.longitude
    }

}