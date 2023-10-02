package com.neqabty.healthcare.chefaa.address.presentation.view.adressscreen


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.presentation.view.selectlocation.SelectLocationActivity
import com.neqabty.healthcare.chefaa.orders.presentation.placeprescriptionscreen.CheckOutActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.PermissionUtils
import com.neqabty.healthcare.core.utils.PermissionsCallback
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.CehfaaActivityAddressesBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddressesActivity : BaseActivity<CehfaaActivityAddressesBinding>() {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = CehfaaActivityAddressesBinding.inflate(layoutInflater)

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
                if (!checkGPS()){
                    buildAlertMessageNoGps()
                    return
                }
                Constants.selectedAddress = addressItem
                startActivity(Intent(this@AddressesActivity, CheckOutActivity::class.java))
                finish()
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
            startActivity(Intent(this@AddressesActivity, SelectLocationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        addressViewModel.getUser(Constants.mobileNumber)
    }

}