package com.neqabty.recruitment.modules.personalinfo.view


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.neqabty.recruitment.R
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.core.utils.Status
import com.neqabty.recruitment.databinding.ActivityPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>() {

    private val mAdapter = CustomAdapter()
    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()
    override fun getViewBinding() = ActivityPersonalInfoBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "البيانات الشخصية")

        personalInfoViewModel.getEngineerInfo("")
        personalInfoViewModel.engineer.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        Log.e("SUCCESS", resource.data!!.mobile)
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        binding.spMarital.adapter = mAdapter
        binding.spGovernment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//                if (filtersData != null && i != 0) {
//                    governorate = filtersData?.governorates?.get(i - 1)
//                    selectedGovernorate = i
//                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        personalInfoViewModel.getMaritalStatus()
        personalInfoViewModel.maritalStatus.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        binding.personalInfoContainer.setOnClickListener {
            if (binding.engineerData.isVisible){
                binding.engineerData.visibility = View.GONE
                binding.personalInfoArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }else{
                binding.engineerData.visibility = View.VISIBLE
                binding.personalInfoArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
        }

        binding.contactInfoContainer.setOnClickListener {
            if (binding.contactData.isVisible){
                binding.contactData.visibility = View.GONE
                binding.contactArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }else{
                binding.contactData.visibility = View.VISIBLE
                binding.contactArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
        }

        binding.addressContainer.setOnClickListener {
            if (binding.addressData.isVisible){
                binding.addressData.visibility = View.GONE
                binding.addressArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }else{
                binding.addressData.visibility = View.VISIBLE
                binding.addressArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
        }

        binding.workContainer.setOnClickListener {
            if (binding.workData.isVisible){
                binding.workData.visibility = View.GONE
                binding.workArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }else{
                binding.workData.visibility = View.VISIBLE
                binding.workArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
        }

        binding.studyingContainer.setOnClickListener {
            if (binding.studyingData.isVisible){
                binding.studyingData.visibility = View.GONE
                binding.studyingArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }else{
                binding.studyingData.visibility = View.VISIBLE
                binding.studyingArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
        }
    }
}