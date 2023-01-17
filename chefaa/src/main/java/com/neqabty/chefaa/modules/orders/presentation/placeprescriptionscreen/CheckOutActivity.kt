package com.neqabty.chefaa.modules.orders.presentation.placeprescriptionscreen


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.lifecycleScope
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Cart
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.data.Constants.selectedAddress
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.FileUtils
import com.neqabty.chefaa.core.utils.Status
import com.neqabty.chefaa.databinding.CehfaaActivityCheckOutBinding
import com.neqabty.chefaa.modules.verifyuser.view.VerifyUserActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


@AndroidEntryPoint
class CheckOutActivity : BaseActivity<CehfaaActivityCheckOutBinding>(), LocationListener {
    private val placeOrderViewModel: PlaceOrderViewModel by viewModels()
    var total: Float = 0.0f
    private lateinit var dialog: AlertDialog
    private val mAdapter = CheckoutCartAdapter()
    private lateinit var photoAdapter: CheckoutPhotosAdapter
    private var totalPrice = 0.0
    private var deviceName = ""
    private var currentLocation = ""
    private var deliveryNote = ""
    private lateinit var locationManager: LocationManager

    override fun getViewBinding() = CehfaaActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }catch (e: Exception){

        }

        getDeviceName()
        updatePrice()
        binding.addressType.text = selectedAddress?.title
        binding.addressDetails.text = "شارع ${selectedAddress?.address}, مبنى رقم ${selectedAddress?.buildingNo}, رقم الطابق ${selectedAddress?.floorNo}, شقة رقم ${selectedAddress?.apartmentNo}"

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .setCancelable(false)
            .build()

        photoAdapter = CheckoutPhotosAdapter(this)
        updateView()

        binding.photosRv.adapter = photoAdapter
        binding.productRv.adapter = mAdapter
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
                                reLaunchHomeActivity(this)
                            }
                            205 -> {
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
                        binding.checkout.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun getDeviceName(){
        val model = Build.MODEL
        val name = Settings.Global.getString(contentResolver, "device_name")
        val brand = Build.BRAND

        deviceName = "$brand, $name, $model"
    }
    private fun updatePrice() {
        totalPrice = 0.0
        for (item in cart.productList){
            totalPrice += item.productEntity?.price!!.times(item.quantity.toDouble())
        }

//        binding.subTotalValue.text = "$totalPrice جنيه"
//        binding.totalValue.text = "$totalPrice جنيه"
    }


    private fun updateView() {
        ///// checkout btn and Empty view
        if (cart.size == 0){
            binding.clEmptyCart.visibility = View.VISIBLE
        }else{
            binding.clEmptyCart.visibility = View.GONE
        }

        /////Images recyclerView
        if (cart.imageList.isNotEmpty()) {
            binding.photosRv.visibility = View.VISIBLE
            photoAdapter.submitList()
        } else {
            binding.llPhotos.visibility = View.GONE
        }

        /////Products recyclerView
        if(cart.productList.isNotEmpty()) {
            binding.productRv.visibility = View.VISIBLE
            mAdapter.submitList()
        } else {
            binding.llProducts.visibility = View.GONE
        }

        if(cart.note != null){
            binding.noteTv.visibility = View.VISIBLE
            binding.noteTv.setText(cart.note!!.note)
        } else {
            binding.clNote.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.checkout.visibility = View.VISIBLE
    }

    fun checkOut(view: View) {

        if (!checkGPS()){
            buildAlertMessageNoGps()
            return
        }

        deliveryNote = binding.noteContent.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                cart.imageList.map {
                    it.image = "data:image/png;base64," + Base64.encodeToString(
                        File(it.imageUri!!.path).readBytes(),
                        Base64.DEFAULT
                    )
                }
                placeOrderViewModel.placePrescriptionImages(selectedAddress?.id!!,  deviceName, currentLocation, deliveryNote)
            }
            binding.checkout.visibility = View.GONE
    }

    override fun onLocationChanged(location: Location) {
        currentLocation = "${location.latitude},${location.longitude}"
    }

}