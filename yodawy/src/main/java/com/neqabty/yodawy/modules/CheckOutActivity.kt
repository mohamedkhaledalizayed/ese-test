package com.neqabty.yodawy.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCheckOutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
    var total: Float = 0.0f
    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)

        binding.tvAddress.text = Constants.selectedAddress.addressName


        for (item in cartItems){
            total += (item.first.salePrice * item.first.quantity)
        }

        binding.totalValue.text = "$total"
    }

    fun confirmOrder(view: View) {
        startActivity(Intent(this, ConfirmCheckoutActivity::class.java))
    }
}