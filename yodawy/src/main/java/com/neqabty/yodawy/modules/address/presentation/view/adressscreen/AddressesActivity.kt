package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen.AddAddressActivity
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : AppCompatActivity() {
    private val addressViewModel: AddressViewModel by viewModels()
    private val mAdapter = AddressAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)
        addressViewModel.getUser("01090100670","3608662")
        addressViewModel.user.observe(this){
            Log.e("user",it.yodawyId)
        }
        findViewById<RecyclerView>(R.id.address_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            AddressAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                startActivity(Intent(this@AddressesActivity, HomeActivity::class.java))
            }
        }
    }

    fun addAddress(view: View) {
        startActivity(Intent(this@AddressesActivity, AddAddressActivity::class.java))
    }
}