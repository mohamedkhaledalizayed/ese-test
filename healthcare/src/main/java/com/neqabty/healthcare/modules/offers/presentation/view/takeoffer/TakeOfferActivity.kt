package com.neqabty.healthcare.modules.offers.presentation.view.takeoffer


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.databinding.ActivityTakeOfferBinding
import com.neqabty.healthcare.modules.offers.presentation.view.payment.PaymentActivity
import com.neqabty.core.ui.BaseActivity
class TakeOfferActivity : BaseActivity<ActivityTakeOfferBinding>() {

    override fun getViewBinding() = ActivityTakeOfferBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "دعوة 8 أصدقاء للحصول على الخصم")

        binding.exchangeBtn.setOnClickListener { startActivity(Intent(this, PaymentActivity::class.java)) }
    }
}