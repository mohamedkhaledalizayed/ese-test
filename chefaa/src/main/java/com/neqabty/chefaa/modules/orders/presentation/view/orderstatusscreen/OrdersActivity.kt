package com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.Status
import com.neqabty.chefaa.databinding.ActivityOrdersBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen.OrderDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersActivity : BaseActivity<ActivityOrdersBinding>() {
    private val ordersViewModel: OrdersViewModel by viewModels()
    private var pageNumber = 0
    lateinit var mLayoutManager: LinearLayoutManager
    private val mAdapter = OrdersAdapter()
    override fun getViewBinding() = ActivityOrdersBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.orders_history)

        loadMoreData()
        ordersViewModel.orders.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isEmpty()){
                            if (mAdapter.itemCount == 0){
                                binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "لا يوجد طلبات", "لم تقم باى طلب من قبل")
                            }else{
                                binding.progressActivity.showContent()
                            }
                        }else{
                            binding.progressActivity.showContent()
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "خطا", resource.message)
                    }
                }
            }
        }

        mLayoutManager = LinearLayoutManager(this)
        binding.ordersRecycler.layoutManager = mLayoutManager
        binding.ordersRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OrdersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(order: OrderEntity) {
                val intent: Intent = Intent(this@OrdersActivity, OrderDetailsActivity::class.java)
                intent.putExtra("orderId", order)
                startActivity(intent)
            }
        }
    }

    private fun loadMoreData() {
        ordersViewModel.getOrders(Constants.userNumber, 10, pageNumber)
    }
}