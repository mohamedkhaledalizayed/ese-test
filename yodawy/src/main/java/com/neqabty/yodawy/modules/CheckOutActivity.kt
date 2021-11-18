package com.neqabty.yodawy.modules

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.data.Constants.selectedAddress
import com.neqabty.yodawy.core.data.Constants.yodawyId
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.FileUtils
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivityCheckOutBinding
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import com.neqabty.yodawy.modules.orders.data.model.request.OrderRequest
import com.neqabty.yodawy.modules.orders.presentation.view.placeorderscreen.PlacePrescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
    private val placePrescriptionViewModel: PlacePrescriptionViewModel by viewModels()
    var total: Float = 0.0f
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)


        binding.addressType.text = selectedAddress.addressName
        binding.addressDetails.text = "شارع ${selectedAddress.address}, مبنى رقم ${selectedAddress.buildingNumber}, رقم الطابق ${selectedAddress.floor}, شقة رقم ${selectedAddress.apt}"

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()

        placePrescriptionViewModel.placeImagesResult.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        imageList.clear()
                        val bundle = Bundle()
                        bundle.putString("user_number", Constants.userNumber)
                        bundle.putString("mobile_number", Constants.mobileNumber)
                        bundle.putString("jwt", Constants.jwt)
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        if (imageList.isNotEmpty()){
            binding.totalValue.text = "To be confirmed"
        }else{
            for (item in cartItems){
                total += (item.first.salePrice * item.first.quantity)
            }
            binding.totalValue.text = "$total"
        }

    }
    @NonNull
    private fun createPartFromString(content: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, content)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @NonNull
    private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part {
        val file = FileUtils.getFile(this, fileUri)
        val requestFile = RequestBody.create(
            contentResolver.getType(fileUri).toString().toMediaTypeOrNull(),
            file
        )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
    }

    fun confirmOrder(view: View) {


        if (imageList.isNotEmpty()){
            val multipartList = ArrayList<MultipartBody.Part>()

            imageList.mapIndexed { index, image ->
                val parts: MultipartBody.Part = prepareFilePart("Prescription$index", image)
                multipartList.add(parts)
            }

            val gson = Gson()
            val json: String = gson.toJson(
                OrderRequest(
                    addressId = selectedAddress.adressId,
                    notes = "Order Note",
                    yodawyId = yodawyId,
                    plan = "A"
                ))
            val order: RequestBody = createPartFromString("{\"AddressId\":\"${selectedAddress.adressId}\",\"Notes\":\"Order Note\",\"YodawyId\":\"$yodawyId\",\"Plan\":\"A\"}")
            placePrescriptionViewModel.placePrescriptionImages(order = order, images = multipartList)
        }else{
            startActivity(Intent(this, ConfirmCheckoutActivity::class.java))
        }
    }
}