package com.neqabty.chefaa.modules.address.presentation.view.adressscreen


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.Status.*
import com.neqabty.chefaa.databinding.CehfaaActivityAddressesBinding
import com.neqabty.chefaa.modules.SelectLocationActivity
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseActivity<CehfaaActivityAddressesBinding>(), LocationListener {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private lateinit var locationManager: LocationManager
    override fun getViewBinding() = CehfaaActivityAddressesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.addresses)


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }catch (e: Exception){

        }
        addressViewModel.user.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    SUCCESS -> {
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
                    ERROR -> {
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
                finish()
            }
        }

        binding.addAddress.setOnClickListener {

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