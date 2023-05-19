package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.isMobileValid
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
        if(Constants.forTesting){
            binding.etName.setText("mona")
            binding.etMobile.setText("01111111111")
        }
        binding.bNext.setOnClickListener {
            if(binding.etName.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_reference_name), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!binding.etMobile.text.toString().isMobileValid()){
                Toast.makeText(this, getString(R.string.enter_reference_number), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            SubmitClientData.entity.clientInfo.refContactName = binding.etName.text.toString()
            SubmitClientData.entity.clientInfo.refContactNum = binding.etMobile.text.toString()
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