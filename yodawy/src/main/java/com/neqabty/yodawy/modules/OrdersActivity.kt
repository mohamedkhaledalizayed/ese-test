package com.neqabty.yodawy.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressAdapter
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity

class OrdersActivity : AppCompatActivity() {

    private val mAdapter = OrdersAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Your Orders"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        findViewById<RecyclerView>(R.id.orders_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                startActivity(Intent(this@OrdersActivity, OrderDetailsActivity::class.java))
            }
        }
    }
}