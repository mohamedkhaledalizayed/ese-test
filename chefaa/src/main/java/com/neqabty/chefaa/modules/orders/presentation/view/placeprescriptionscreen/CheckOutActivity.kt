package com.neqabty.chefaa.modules.orders.presentation.view.placeprescriptionscreen
//
//import android.app.AlertDialog
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.activity.viewModels
//import androidx.annotation.NonNull
//import com.google.gson.Gson
//import com.neqabty.chefaa.R
//import com.neqabty.chefaa.core.data.Constants
//import com.neqabty.chefaa.core.data.Constants.cartItems
//import com.neqabty.chefaa.core.data.Constants.imageList
//import com.neqabty.chefaa.core.data.Constants.selectedAddress
//import com.neqabty.chefaa.core.data.Constants.yodawyId
//import com.neqabty.chefaa.core.ui.BaseActivity
//import com.neqabty.chefaa.core.utils.FileUtils
//import com.neqabty.chefaa.core.utils.Status
//import com.neqabty.chefaa.databinding.ActivityCheckOutBinding
//import com.neqabty.chefaa.modules.orders.presentation.view.placeorderscreen.ConfirmCheckoutActivity
//import com.neqabty.chefaa.modules.address.presentation.view.homescreen.HomeActivity
//import com.neqabty.chefaa.modules.orders.data.model.request.OrderRequest
//import com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen.OrderDetailsActivity
//import dagger.hilt.android.AndroidEntryPoint
//import dmax.dialog.SpotsDialog
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//
//@AndroidEntryPoint
//class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
//    private val placePrescriptionViewModel: PlacePrescriptionViewModel by viewModels()
//    var total: Float = 0.0f
//    private lateinit var dialog: AlertDialog
//    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        setupToolbar(titleResId = R.string.place_order)
//
//
//        binding.addressType.text = selectedAddress.addressName
//        binding.addressDetails.text = "شارع ${selectedAddress.address}, مبنى رقم ${selectedAddress.buildingNumber}, رقم الطابق ${selectedAddress.floor}, شقة رقم ${selectedAddress.apt}"
//
//        dialog = SpotsDialog.Builder()
//            .setContext(this)
//            .setMessage(getString(R.string.please_wait))
//            .build()
//
//        placePrescriptionViewModel.placeImagesResult.observe(this){
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.LOADING -> {
//                        dialog.show()
//                    }
//                    Status.SUCCESS -> {
//                        dialog.dismiss()
//                        imageList.clear()
//                        val bundle = Bundle()
//                        bundle.putString("user_number", Constants.userNumber)
//                        bundle.putString("mobile_number", Constants.mobileNumber)
//                        bundle.putString("jwt", Constants.jwt)
//                        bundle.putString("orderId", resource.data)
//                        bundle.putBoolean("navigation", true)
//                        val intent = Intent(this, OrderDetailsActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        intent.putExtras(bundle)
//                        startActivity(intent)
//                        finish()
//                    }
//                    Status.ERROR -> {
//                        dialog.dismiss()
//                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }
//
//        if (imageList.isNotEmpty()){
//            binding.totalValue.text = "To be confirmed"
//        }else{
//            for (item in cartItems){
//                total += (item.first.salePrice * item.second)
//            }
//            binding.totalValue.text = "$total"
//        }
//
//    }
//    @NonNull
//    private fun createPartFromString(content: String): RequestBody {
//        return RequestBody.create(MultipartBody.FORM, content)
//    }
//
//    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//    @NonNull
//    private fun prepareFilePart(
//        partName: String,
//        fileUri: Uri
//    ): MultipartBody.Part {
//        val file = FileUtils.getFile(this, fileUri)
//        val requestFile = RequestBody.create(
//            contentResolver.getType(fileUri).toString().toMediaTypeOrNull(),
//            file
//        )
//        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
//    }
//
//    fun confirmOrder(view: View) {
//
//
//        if (imageList.isNotEmpty()){
//            val multipartList = ArrayList<MultipartBody.Part>()
//
//            imageList.mapIndexed { index, image ->
//                val parts: MultipartBody.Part = prepareFilePart("Prescription$index", image)
//                multipartList.add(parts)
//            }
//
//            val gson = Gson()
//            val json: String = gson.toJson(
//                OrderRequest(
//                    addressId = selectedAddress.adressId,
//                    notes = "Order Note",
//                    yodawyId = yodawyId,
//                    plan = "A"
//                ))
//            val order: RequestBody = createPartFromString("{\"AddressId\":\"${selectedAddress.adressId}\",\"Notes\":\"Order Note\",\"YodawyId\":\"$yodawyId\",\"Plan\":\"A\"}")
//            placePrescriptionViewModel.placePrescriptionImages(order = order, images = multipartList)
//        }else{
//            startActivity(Intent(this, ConfirmCheckoutActivity::class.java))
//        }
//    }
//}