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
                        binding.name.setText(resource.data?.name)
                        binding.phone.setText(resource.data?.phone)
                        binding.birthdate.setText(resource.data?.dateOfBirth)
                        binding.nationalId.setText(resource.data?.nationalId)
                        binding.membershipId.setText(resource.data?.membershipId)
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }




        personalInfoViewModel.getMaritalStatus()
        personalInfoViewModel.maritalStatus.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val mAdapter = CustomAdapter()
                        binding.spMarital.adapter = mAdapter
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        personalInfoViewModel.getNationalities()
        personalInfoViewModel.nationalities.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val mAdapter = CustomAdapter()
                        binding.spNationality.adapter = mAdapter
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