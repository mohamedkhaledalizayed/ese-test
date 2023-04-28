package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityEmploymentDetailsTwoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmploymentDetails2Activity : BaseActivity<ActivityEmploymentDetailsTwoBinding>() {
    override fun getViewBinding() = ActivityEmploymentDetailsTwoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.employment_details)
        initializeViews()
    }

    private fun initializeViews() {
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
            EmploymentDetails3Activity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}