package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityReviewYourDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewYourDataActivity : BaseActivity<ActivityReviewYourDataBinding>() {
    override fun getViewBinding() = ActivityReviewYourDataBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.review_your_data)
        initializeViews()
    }

    private fun initializeViews() {
        binding.tvName.text = sharedPreferences.name
        binding.tvNatId.text = sharedPreferences.nationalId

        binding.bNext.setOnClickListener {
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    //region
    private fun navigate() {
        val mainIntent = Intent(
            this,
            ResidenceActivity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}