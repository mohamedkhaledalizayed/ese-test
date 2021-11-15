package com.neqabty.yodawy.modules

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.yodawyId
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivityCheckOutBinding
import com.neqabty.yodawy.modules.orders.domain.entity.ItemParam
import com.neqabty.yodawy.modules.orders.presentation.view.placeorderscreen.PlaceOrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
    private val placeOrderViewModel:PlaceOrderViewModel by viewModels()
    private lateinit var dialog: AlertDialog
    var total: Float = 0.0f
    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()

        binding.tvAddress.text = Constants.selectedAddress.addressName

        placeOrderViewModel.placeOrderResult.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!){
                            cartItems.clear()
                            Toast.makeText(this, getString(R.string.order_is_placed), Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }
        }


        for (item in cartItems){
            total += (item.first.salePrice * item.first.quantity)
        }

        binding.totalValue.text = "$total"
    }

    fun confirmOrder(view: View) {
        placeOrderViewModel.placeOrder(Constants.selectedAddress.adressId,Constants.mobileNumber,"notes", yodawyId, cartItems.map {
            ItemParam(it.first.id,it.first.quantity)
        })
    }
}