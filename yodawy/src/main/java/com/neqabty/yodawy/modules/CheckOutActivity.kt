package com.neqabty.yodawy.modules

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
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
    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()

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
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    fun confirmOrder(view: View) {
        placeOrderViewModel.placeOrder(Constants.selectedAddressId,Constants.mobileNumber,"notes","plan",Constants.cartItems.map {
            ItemParam(it.first.id,it.second)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CartActivity::class.java))
        finish()
    }
}