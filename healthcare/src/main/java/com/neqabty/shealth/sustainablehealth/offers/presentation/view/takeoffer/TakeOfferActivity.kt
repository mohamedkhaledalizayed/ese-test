package com.neqabty.shealth.sustainablehealth.offers.presentation.view.takeoffer


import android.content.Intent
import android.os.Bundle
import com.neqabty.shealth.databinding.ActivityTakeOfferBinding
import com.neqabty.shealth.sustainablehealth.offers.presentation.view.payment.PaymentActivity
import com.neqabty.shealth.core.ui.BaseActivity
class TakeOfferActivity : BaseActivity<ActivityTakeOfferBinding>() {

    override fun getViewBinding() = ActivityTakeOfferBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "دعوة 8 أصدقاء للحصول على الخصم")

        binding.exchangeBtn.setOnClickListener { startActivity(Intent(this, PaymentActivity::class.java)) }
    }
}