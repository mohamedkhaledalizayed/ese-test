package com.neqabty.healthcare.pharmacymart.address.ui.addresseslist


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.selectedAddressPharmacyMart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.PermissionUtils
import com.neqabty.healthcare.core.utils.PermissionsCallback
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityAddressesBinding
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressEntity
import com.neqabty.healthcare.pharmacymart.address.ui.PharmacyMartAddressViewModel
import com.neqabty.healthcare.pharmacymart.address.ui.selectlocation.PharmacyMartSelectLocationActivity
import com.neqabty.healthcare.pharmacymart.orders.ui.addorder.AddOrderActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddressesActivity : BaseActivity<ActivityAddressesBinding>() {

    private val mAdapter = AddressAdapter()
    private lateinit var dialog: AlertDialog
    private val viewModel: PharmacyMartAddressViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun getViewBinding() = ActivityAddressesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.backBtn.setOnClickListener {
            finish()
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

            override fun setOnDeleteClickListener(addressIId: String) {
                viewModel.deleteAddresses(addressIId)
            }
        }

        viewModel.addressStatus.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressActivity.showLoading()
                }
                Status.SUCCESS ->{
                    binding.progressActivity.showContent()
                    Toast.makeText(this@AddressesActivity, it.data?.message, Toast.LENGTH_LONG).show()
                    if (it.data!!.status){
                        viewModel.getAddresses()
                    }
                }
                Status.ERROR ->{
                    binding.progressActivity.showContent()
                    Toast.makeText(this@AddressesActivity, "حدث خطاء ما.", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.addAddressBtn.setOnClickListener {

            if (!checkGPS()){
                buildAlertMessageNoGps()
                return@setOnClickListener
            }

            requestLocationPermissions()

        }

    }

    private fun requestLocationPermissions() {

        PermissionUtils.requestLocationPermissions(this, object : PermissionsCallback {
            override fun onPermissionRequest(granted: Boolean) {
                if (granted){
                    dialog.show()
                    openMap()
                }else{
                    showSettingsDialog("يحتاج هذا التطبيق الاذن للوصول الى موقعك الحالى. يمكنك منحهم في إعدادات التطبيق.")
                }
            }
        })

    }

    @SuppressLint("MissingPermission")
    private fun openMap() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@AddressesActivity)
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token).addOnCompleteListener {
            dialog.dismiss()
            Constants.latitude = it.result.latitude
            Constants.longitude = it.result.longitude
            startActivity(Intent(this@AddressesActivity, PharmacyMartSelectLocationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddresses()
    }

}