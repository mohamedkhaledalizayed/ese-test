package com.neqabty.chefaa.modules.address.presentation.view.adressscreen

import android.content.Intent
import android.os.Bundle
import com.neqabty.chefaa.core.utils.Status.ERROR
import com.neqabty.chefaa.core.utils.Status.LOADING
import com.neqabty.chefaa.core.utils.Status.SUCCESS
import androidx.activity.viewModels
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.databinding.ActivityAddressesBinding
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.yodawy.modules.orders.presentation.view.placeprescriptionscreen.CheckOutActivity
import com.neqabty.chefaa.modules.SelectLocationActivity
import com.neqabty.chefaa.modules.address.domain.entity.AddressEntity
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
            override fun setOnItemClickListener(addressItem: AddressEntity) {
                Constants.selectedAddress = addressItem

                startActivity(Intent(this@AddressesActivity, CheckOutActivity::class.java))
                finish()
            }
        }

        binding.addAddress.setOnClickListener {
            startActivity(Intent(this@AddressesActivity, SelectLocationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        addressViewModel.getUser(Constants.userNumber, Constants.mobileNumber)
    }
}