package com.neqabty.yodawy.modules

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.selectedAddress
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivityConfirmCheckoutBinding
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import com.neqabty.yodawy.modules.orders.domain.entity.ItemParam
import com.neqabty.yodawy.modules.orders.presentation.view.placeorderscreen.PlaceOrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ConfirmCheckoutActivity : BaseActivity<ActivityConfirmCheckoutBinding>() {
    private val placeOrderViewModel: PlaceOrderViewModel by viewModels()
    private lateinit var dialog: AlertDialog
    private val mAdapter = ProductsAdapter()
    var total: Float = 0.0f
    override fun getViewBinding() = ActivityConfirmCheckoutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()

        binding.productsRecycler.adapter = mAdapter
        mAdapter.submitList(Constants.cartItems)
        mAdapter.onItemClickListener = object :
            ProductsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }
        }

        binding.addressType.text = selectedAddress.addressName
        binding.addressDetails.text = "شارع ${selectedAddress.address}, مبنى رقم ${selectedAddress.buildingNumber}, رقم الطابق ${selectedAddress.floor}, شقة رقم ${selectedAddress.apt}"
        placeOrderViewModel.placeOrderResult.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!){
                            Constants.cartItems.clear()
                            Toast.makeText(this, getString(R.string.order_is_placed), Toast.LENGTH_LONG).show()

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
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }
        }


        for (item in Constants.cartItems){
            total += (item.first.salePrice * item.first.quantity)
        }
        binding.totalValue.text = "$total جنيه"
        binding.totalPayment.text = "$total جنيه"
    }

    fun confirmOrder(view: View) {
        placeOrderViewModel.placeOrder(selectedAddress.adressId,Constants.mobileNumber,"notes",
            Constants.yodawyId, Constants.cartItems.map {
            ItemParam(it.first.id,it.first.quantity)
        })
    }
}