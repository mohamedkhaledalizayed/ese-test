package com.neqabty.healthcare.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityEmploymentDetailsOneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmploymentDetails1Activity : BaseActivity<ActivityEmploymentDetailsOneBinding>() {
    override fun getViewBinding() = ActivityEmploymentDetailsOneBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.employment_details)
        initializeViews()
    }

    private fun initializeViews() {
        binding.bNext.setOnClickListener {
            SubmitClientData.entity.employmentDetails.employmentStatus = findViewById<RadioButton>(binding.rgEmploymentType.checkedRadioButtonId).text.toString()
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
            EmploymentDetails2Activity::class.java
        )
        startActivity(mainIntent)
    }
// endregion
}