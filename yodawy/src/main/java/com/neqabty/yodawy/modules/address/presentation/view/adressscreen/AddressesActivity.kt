package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.content.Intent
import android.os.Bundle
import com.neqabty.yodawy.core.utils.Status.ERROR
import com.neqabty.yodawy.core.utils.Status.LOADING
import com.neqabty.yodawy.core.utils.Status.SUCCESS
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.utils.LocaleHelper
import com.neqabty.yodawy.databinding.ActivityAddressesBinding
import com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseActivity<ActivityAddressesBinding>() {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()

    override fun getViewBinding() = ActivityAddressesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.addresses)

        Constants.userNumber = intent.extras!!.getString("user_number", "")
        Constants.mobileNumber = intent.extras!!.getString("mobile_number", "")
//        Constants.jwt = intent.extras!!.getString("jwt", Constants.jwt)


        addressViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    SUCCESS -> {
                        if (resource.data?.addresses!!.isEmpty()){
                            binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "لا يوجد عناوين", "برجاء إضافة عنوان")
                        }else{
                            Constants.yodawyId = resource.data.yodawyId
                            binding.progressActivity.showContent()
                            mAdapter.submitList(resource.data.addresses)
                        }
                    }
                    ERROR -> {
                        binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "خطا", resource.message)
                    }
                }
            }

        }
        binding.addressRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: String) {
                Constants.selectedAddressId = id
                startActivity(Intent(this@AddressesActivity, HomeActivity::class.java))
            }
        }

        binding.addAddress.setOnClickListener {
            startActivity(Intent(this@AddressesActivity, AddAddressActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        addressViewModel.getUser(Constants.userNumber, Constants.mobileNumber)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }
}