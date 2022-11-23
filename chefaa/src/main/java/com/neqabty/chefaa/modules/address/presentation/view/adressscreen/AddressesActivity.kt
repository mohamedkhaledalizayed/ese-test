package com.neqabty.chefaa.modules.address.presentation.view.adressscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.neqabty.chefaa.core.utils.Status.ERROR
import com.neqabty.chefaa.core.utils.Status.LOADING
import com.neqabty.chefaa.core.utils.Status.SUCCESS
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.databinding.CehfaaActivityAddressesBinding
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.modules.SelectLocationActivity
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.orders.presentation.placeprescriptionscreen.CheckOutActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseActivity<CehfaaActivityAddressesBinding>() {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()
    private var latitude = 30.062768087142633
    private var longitude = 31.245639547705647
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun getViewBinding() = CehfaaActivityAddressesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.addresses)


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

//                startActivity(Intent(this@AddressesActivity, CheckOutActivity::class.java))
                finish()
            }
        }

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        requestPermissionLauncher =
//            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//                if (isGranted) {
//                    checkLocation()
//                } else {
//                    showDialogAndStayHere()
//                }
//            }

        binding.addAddress.setOnClickListener {
//            checkLocation()
            val intent = Intent(this@AddressesActivity, SelectLocationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDialogAndStayHere() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.chefaa_permission))
        alertDialog.setMessage(getString(R.string.chefaa_permission_message))
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, getString(R.string.chefaa_permission_agree)
        ) { dialog, _ ->
            checkLocation()
        }

        alertDialog.show()

    }

    fun grantLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun checkLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            grantLocationPermission()
            return
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.latitude?.let { latitude = it }
                location?.longitude?.let { longitude = it }
                val intent = Intent(this@AddressesActivity, SelectLocationActivity::class.java)
                intent.putExtra("LAT",latitude)
                intent.putExtra("LNG",longitude)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        addressViewModel.getUser(Constants.userNumber, Constants.mobileNumber)
    }
}