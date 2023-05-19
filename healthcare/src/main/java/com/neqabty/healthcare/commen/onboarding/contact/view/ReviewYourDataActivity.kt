package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.isValidEmail
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

        binding.spMaritalStatus.adapter = ArrayAdapter(this, R.layout.spinner_item, listOf(getString(R.string.choose_marital_status),
            getString(R.string.marital_status_single),
            getString(R.string.marital_status_married),
            getString(R.string.marital_status_divorced),
            getString(R.string.marital_status_widow)))
        binding.spMaritalStatus.setSelection(0)

        binding.bNext.setOnClickListener {
            if (binding.spMaritalStatus.selectedItemPosition == 0){
                Toast.makeText(this, resources.getString(R.string.enter_marital_status), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!binding.etEmail.text.toString().isValidEmail()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.enter_correct_email),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            SubmitClientData.entity.clientInfo.userName = binding.tvName.text.toString()
            SubmitClientData.entity.clientInfo.phone = sharedPreferences.mobile
            SubmitClientData.entity.nationalId = sharedPreferences.nationalId
            SubmitClientData.entity.userInputNationalId = sharedPreferences.nationalId
            SubmitClientData.entity.clientInfo.homeAddress = binding.address.text.toString()
            SubmitClientData.entity.clientInfo.maritalStatus = sharedPreferences.nationalId
            SubmitClientData.entity.clientInfo.email = binding.spMaritalStatus.selectedItem.toString()
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }

        if(Constants.forTesting){
            binding.tvName.text = "name"
            binding.spMaritalStatus.setSelection(1)
            binding.etEmail.setText("mona@gmail.com")
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