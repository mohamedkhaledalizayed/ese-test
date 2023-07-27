package com.neqabty.healthcare.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityEmploymentDetailsTwoBinding
import com.neqabty.healthcare.onboarding.contact.domain.entity.GovEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmploymentDetails2Activity : BaseActivity<ActivityEmploymentDetailsTwoBinding>() {
    val employmentDetailsViewModel: EmploymentDetailsViewModel by viewModels()
    override fun getViewBinding() = ActivityEmploymentDetailsTwoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.employment_details)

        employmentDetailsViewModel.getLookups()
        employmentDetailsViewModel.govList.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        binding.clHolder.visibility = View.VISIBLE
                        initializeViews()
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun initializeViews() {
        if(Constants.forTesting){
            binding.etName.setText("name")
            binding.etField.setText("field")
            binding.etAddress.setText("address")
        }

        initializeSpinners()
        binding.bNext.setOnClickListener {
            if(binding.etName.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_company_name), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(binding.etField.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_field), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(binding.etAddress.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_address), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            SubmitClientData.entity.employmentDetails.companyName = binding.etName.text.toString()
            SubmitClientData.entity.employmentDetails.employmentSector = binding.etField.text.toString()
            SubmitClientData.entity.employmentDetails.workGov = binding.spGov.selectedItem.toString()
            SubmitClientData.entity.employmentDetails.workArea = binding.spArea.selectedItem.toString()
            SubmitClientData.entity.employmentDetails.workAddress = binding.etAddress.text.toString()
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    //region
    fun initializeSpinners() {
        renderGoverns()
    }

    fun renderGoverns() {
        binding.spGov.adapter = ArrayAdapter(this, R.layout.spinner_item, employmentDetailsViewModel.govList.value?.data?.toMutableList() as MutableList<GovEntity>)
        binding.spGov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                renderAreas()
            }
        }
        binding.spGov.setSelection(0)
    }

    fun renderAreas() {
        val areas = employmentDetailsViewModel.govList.value?.data?.find { govEntity ->
            govEntity.governorateAr.equals((binding.spGov.selectedItem as GovEntity).governorateAr)
        }!!.areas

        binding.spArea.adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            areas
        )
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            }
        }
    }
    private fun navigate() {
        val mainIntent = Intent(
            this,
            EmploymentDetails3Activity::class.java
        )
        startActivity(mainIntent)
    }
// endregion
}