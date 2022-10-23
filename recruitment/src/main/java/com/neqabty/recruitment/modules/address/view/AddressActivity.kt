package com.neqabty.recruitment.modules.address.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.core.utils.Status
import com.neqabty.recruitment.databinding.ActivityAddressBinding
import com.neqabty.recruitment.modules.personalinfo.view.CustomAdapter
import com.neqabty.recruitment.modules.personalinfo.view.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressActivity : BaseActivity<ActivityAddressBinding>() {

    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()

    override fun getViewBinding() = ActivityAddressBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.spGovernment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//                if (filtersData != null && i != 0) {
//                    governorate = filtersData?.governorates?.get(i - 1)
//                    selectedGovernorate = i
//                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        personalInfoViewModel.getCountries()
        personalInfoViewModel.countries.observe(this){
            it.let {
                    resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val mAdapter = CustomAdapter()
                        binding.spCountry.adapter = mAdapter
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        personalInfoViewModel.getGovernorates()
        personalInfoViewModel.governorates.observe(this){
            it.let {
                    resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val mAdapter = CustomAdapter()
                        binding.spGovernment.adapter = mAdapter
                        binding.spOutEgyptGovernment.adapter = mAdapter
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }
    }
}