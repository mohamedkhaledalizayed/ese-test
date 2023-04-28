package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityDependantsBinding
import com.neqabty.healthcare.databinding.ActivityReferenceNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferenceNumberActivity : BaseActivity<ActivityReferenceNumberBinding>() {
    override fun getViewBinding() = ActivityReferenceNumberBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.reference_number)
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
            EmploymentDetails1Activity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}