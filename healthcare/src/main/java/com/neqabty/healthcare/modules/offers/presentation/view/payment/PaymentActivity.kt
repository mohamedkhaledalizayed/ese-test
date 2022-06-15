package com.neqabty.healthcare.modules.offers.presentation.view.payment


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPaymentBinding


class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {

    override fun getViewBinding() = ActivityPaymentBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "عمليه الدفع")
    }
}