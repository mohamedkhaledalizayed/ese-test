package com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.ActivityOrdersBinding
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.modules.OrderDetailsActivity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersActivity : BaseActivity<ActivityOrdersBinding>() {
    private val ordersViewModel: OrdersViewModel by viewModels()

    private val mAdapter = OrdersAdapter()
    override fun getViewBinding() = ActivityOrdersBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.orders_history)

        ordersViewModel.getOrders("01090100670", 10, 0)
        ordersViewModel.orders.observe(this){
            findViewById<RecyclerView>(R.id.orders_recycler).adapter = mAdapter
            mAdapter.submitList(it)
        }
        mAdapter.onItemClickListener = object :
            OrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                startActivity(Intent(this@OrdersActivity, OrderDetailsActivity::class.java))
            }
        }
    }
}