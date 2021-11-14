package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.content.Intent
import android.os.Bundle
import com.neqabty.yodawy.core.utils.Status.ERROR
import com.neqabty.yodawy.core.utils.Status.LOADING
import com.neqabty.yodawy.core.utils.Status.SUCCESS
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.utils.LocaleHelper
import com.neqabty.yodawy.databinding.ActivityAddressesBinding
import com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
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
        Constants.jwt = intent.extras!!.getString("jwt", "")

        addressViewModel.getUser(Constants.userNumber, Constants.mobileNumber)

        addressViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        findViewById<ProgressRelativeLayout>(R.id.progressActivity).showLoading()
                    }
                    SUCCESS -> {
                        if (resource.data?.addresses!!.isEmpty()){
                            findViewById<ProgressRelativeLayout>(R.id.progressActivity).showEmpty(R.drawable.ic_undraw_empty_xct9, "لا يوجد عناوين", "برجاء إضافة عنوان")
                        }else{
                            Constants.yodawyId = resource.data.yodawyId
                            findViewById<ProgressRelativeLayout>(R.id.progressActivity).showContent()
                            mAdapter.submitList(resource.data.addresses)
                        }
                    }
                    ERROR -> {
                        findViewById<ProgressRelativeLayout>(R.id.progressActivity).showEmpty(R.drawable.ic_undraw_access_denied_6w73, "خطا", resource.message)
                    }
                }
            }



        }
        findViewById<RecyclerView>(R.id.address_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: String) {
                Constants.selectedAddressId = id
                startActivity(Intent(this@AddressesActivity, HomeActivity::class.java))
            }
        }

        findViewById<FloatingActionButton>(R.id.add_address).setOnClickListener {
            startActivity(Intent(this@AddressesActivity, AddAddressActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }
}