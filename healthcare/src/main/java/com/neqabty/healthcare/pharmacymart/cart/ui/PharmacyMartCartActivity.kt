package com.neqabty.healthcare.pharmacymart.cart.ui


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPharmacyMartCartBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PharmacyMartCartActivity : BaseActivity<ActivityPharmacyMartCartBinding>() {

    override fun getViewBinding() = ActivityPharmacyMartCartBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}