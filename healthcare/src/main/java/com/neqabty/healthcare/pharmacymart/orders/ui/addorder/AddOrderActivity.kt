package com.neqabty.healthcare.pharmacymart.orders.ui.addorder

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.core.data.Cart
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.data.Constants.selectedAddressPharmacyMart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityAddOrderBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class AddOrderActivity : BaseActivity<ActivityAddOrderBinding>(), LocationListener {


    private val placeOrderViewModel: OrdersViewModel by viewModels()
    var total: Float = 0.0f
    private lateinit var dialog: AlertDialog
    private val mAdapter: OrdersAdapter = OrdersAdapter()
    private var deviceName = ""
    private var currentLocation = ""
    private lateinit var locationManager: LocationManager

    override fun getViewBinding() = ActivityAddOrderBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.headerContainer.setOnClickListener { finish() }

        getDeviceName()

        binding.address.text = "${selectedAddressPharmacyMart?.title}  - شارع  ${selectedAddressPharmacyMart?.address}, مبنى رقم ${selectedAddressPharmacyMart?.buildingNo}, رقم الطابق ${selectedAddressPharmacyMart?.floorNo}, شقة رقم ${selectedAddressPharmacyMart?.apartmentNo}"

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .setCancelable(false)
            .build()

        binding.productRecyclerView.adapter = mAdapter
        placeOrderViewModel.placeImagesResult.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        when (resource.data?.statusCode) {
                            200 -> {
                                Toast.makeText(this, getString(R.string.order_is_placed), Toast.LENGTH_LONG).show()
                                cart = Cart()
                                reLaunchHomeActivityPharmacyMart(this)
                            }
                            405 -> {
                                startActivity(Intent(this, VerifyUserActivity::class.java))
                                Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
                            }407 -> {
                            Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
                        }
                            else -> {
                                Toast.makeText(this, resource.data?.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        binding.completeBtn.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.completeBtn.setOnClickListener {
            checkOut()
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }
    }

    private fun checkOut() {

        if (!checkGPS()){
            buildAlertMessageNoGps()
            return
        }

        if (binding.deliveryPhone.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم التوصيل.", Toast.LENGTH_LONG).show()
            return
        }

        val list = mutableListOf<String>()
        lifecycleScope.launch(Dispatchers.IO) {
             cart.imageList.map {
                list.add("data:image/png;base64," + Base64.encodeToString(
                    File(it.imageUri?.path).readBytes(),
                    Base64.DEFAULT
                ))
            }

            placeOrderViewModel.placePrescriptionImages(
                list = list,
                addressId = selectedAddressPharmacyMart?.id ?: 0,
                deviceInfo = deviceName,
                currentLocation = currentLocation,
                deliveryNote = binding.noteContent.text.toString(),
                deliveryMobile = binding.deliveryPhone.text.toString(),
                orderByText = cart.note?.note ?: ""
            )
        }
        binding.completeBtn.visibility = View.GONE
    }

    private fun getDeviceName(){
        val model = Build.MODEL
        val name = Settings.Global.getString(contentResolver, "device_name")
        val brand = Build.BRAND

        deviceName = "$brand, $name, $model"
    }

    override fun onLocationChanged(location: Location) {
        currentLocation = "${location.latitude},${location.longitude}"
    }

}