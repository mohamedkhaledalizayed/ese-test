package com.neqabty.healthcare.commen.landing


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.commen.syndicates.presentation.view.homescreen.SyndicateActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityLandingPageBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingPageActivity : BaseActivity<ActivityLandingPageBinding>() {

    override fun getViewBinding() = ActivityLandingPageBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.syndicateLayout.setOnClickListener {
            startActivity(Intent(this, SyndicateActivity::class.java))
            finish()
        }

        binding.neqabtyLayout.setOnClickListener {
            startActivity(Intent(this, SehaHomeActivity::class.java))
            finish()
        }
    }
}