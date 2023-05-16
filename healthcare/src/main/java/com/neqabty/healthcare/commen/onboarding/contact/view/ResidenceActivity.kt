package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityResidenceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResidenceActivity : BaseActivity<ActivityResidenceBinding>() {
    val residenceViewModel: ResidenceViewModel by viewModels()
    override fun getViewBinding() = ActivityResidenceBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.residence)

        residenceViewModel.getLookups()
        residenceViewModel.govList.observe(this){

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
        initializeSpinners()

        binding.bNext.setOnClickListener {
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
        binding.spGov.adapter = ArrayAdapter(this, R.layout.spinner_item, residenceViewModel.govList.value?.data?.toMutableList() as MutableList<GovEntity>)
        binding.spGov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                renderAreas()
            }
        }
        binding.spGov.setSelection(0)
    }

    fun renderAreas() {
        val areas = residenceViewModel.govList.value?.data?.find { govEntity ->
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
            DependantsActivity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}