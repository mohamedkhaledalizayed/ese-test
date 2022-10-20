package com.neqabty.healthcare.sustainablehealth.offers.presentation.view.payment


import android.os.Bundle
import com.neqabty.healthcare.databinding.ActivityPaymentBinding
import com.neqabty.healthcare.core.ui.BaseActivity

class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {

    override fun getViewBinding() = ActivityPaymentBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "عمليه الدفع")
    }
}