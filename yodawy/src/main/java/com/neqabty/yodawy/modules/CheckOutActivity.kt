package com.neqabty.yodawy.modules

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCheckOutBinding
import com.neqabty.yodawy.modules.orders.domain.entity.ItemParam
import com.neqabty.yodawy.modules.orders.presentation.view.placeorderscreen.PlaceOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
    private val placeOrderViewModel:PlaceOrderViewModel by viewModels()
    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)
        placeOrderViewModel.placeOrderResult.observe(this){

        }
    }

    fun confirmOrder(view: View) {
        placeOrderViewModel.placeOrder(Constants.selectedAddressId,Constants.mobileNumber,"notes","plan",Constants.cartItems.map {
            ItemParam(it.first.id,it.second)
        })
    }
}