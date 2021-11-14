package com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.databinding.ActivityOrdersBinding
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
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

        ordersViewModel.getOrders(Constants.mobileNumber, 10, 0)
        ordersViewModel.orders.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isEmpty()){
                            binding.progressActivity.showEmpty(R.drawable.ic_undraw_empty_xct9, "لا يوجد طلبات", "لم تقم باى طلب من قبل")
                        }else{
                            binding.progressActivity.showContent()
                            binding.ordersRecycler.adapter = mAdapter
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressActivity.showEmpty(R.drawable.ic_undraw_access_denied_6w73, "خطا", resource.message)
                    }
                }
            }
        }
        mAdapter.onItemClickListener = object :
            OrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                startActivity(Intent(this@OrdersActivity, OrderDetailsActivity::class.java))
            }
        }
    }
}