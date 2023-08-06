package com.neqabty.healthcare.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityEmploymentDetailsThreeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmploymentDetails3Activity : BaseActivity<ActivityEmploymentDetailsThreeBinding>() {
    override fun getViewBinding() = ActivityEmploymentDetailsThreeBinding.inflate(layoutInflater)
    val employmentDetailsViewModel: EmploymentDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.employment_details)
        observeOnSubmitClientStatus()
        initializeViews()
    }

    private fun initializeViews() {
        if(Constants.forTesting){
            binding.etTitle.setText("title")
            binding.etYears.setText("0")
            binding.etSalary.setText("0.0")
        }

        binding.bNext.setOnClickListener {
            if(binding.etTitle.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_title), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(binding.etYears.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_years), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(binding.etSalary.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_salary), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            SubmitClientData.entity.employmentDetails.jobTitle = binding.etTitle.text.toString()
            SubmitClientData.entity.employmentDetails.currentWorkYears = binding.etYears.text.toString()
            SubmitClientData.entity.employmentDetails.appMonthlyIncome = binding.etSalary.text.toString()
            employmentDetailsViewModel.submitClient(SubmitClientData.entity)
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    private fun observeOnSubmitClientStatus() {
        employmentDetailsViewModel.submitClientStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null && resource.data.success) {
                            showAlert(getString(R.string.client_submitted)) {
                                sharedPreferences.isContactSubscriber = true
                                navigate()
                            }
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        showAlert(message = resource.message ?: "") { }
                    }
                }
            }
        }
    }

    //region
    private fun navigate() {
        val mainIntent = Intent(
            this,
            getHomeActivity()
        )
        startActivity(mainIntent)
        finishAffinity()
    }
// endregion
}