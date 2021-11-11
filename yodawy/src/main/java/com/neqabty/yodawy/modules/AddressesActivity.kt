package com.neqabty.yodawy.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.home.presentation.view.homescreen.HomeActivity

class AddressesActivity : AppCompatActivity() {

    private val mAdapter = AddressAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

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