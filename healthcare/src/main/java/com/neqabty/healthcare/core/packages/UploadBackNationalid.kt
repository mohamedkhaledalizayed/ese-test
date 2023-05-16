package com.neqabty.healthcare.core.packages


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityUploadBackNationalidBinding
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity

class UploadBackNationalid : BaseActivity<ActivityUploadBackNationalidBinding>() {
    override fun getViewBinding() = ActivityUploadBackNationalidBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "بيانات الحساب")

        binding.bNext.setOnClickListener {
            startActivity(Intent(this, SehaPaymentActivity::class.java))
        }

    }
}