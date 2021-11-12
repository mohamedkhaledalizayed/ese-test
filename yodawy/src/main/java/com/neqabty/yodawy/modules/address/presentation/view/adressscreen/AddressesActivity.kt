package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.utils.LocaleHelper
import com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : AppCompatActivity() {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

//        supportActionBar!!.title = "العناوين"
        addressViewModel.getUser("3608662","01090100670")
        findViewById<ProgressRelativeLayout>(R.id.progressActivity).showLoading()
        addressViewModel.user.observe(this){
            findViewById<ProgressRelativeLayout>(R.id.progressActivity).showContent()
            mAdapter.submitList(it.addresses)
        }
        findViewById<RecyclerView>(R.id.address_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
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