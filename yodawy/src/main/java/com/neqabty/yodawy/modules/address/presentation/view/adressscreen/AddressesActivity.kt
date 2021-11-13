package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.content.Intent
import android.os.Bundle
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
        findViewById<ProgressRelativeLayout>(R.id.progressActivity).showLoading()
        addressViewModel.user.observe(this){
            Constants.yodawyId = it.yodawyId
            findViewById<ProgressRelativeLayout>(R.id.progressActivity).showContent()
            mAdapter.submitList(it.addresses)
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