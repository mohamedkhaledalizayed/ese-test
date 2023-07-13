package com.neqabty.healthcare.chefaa.orders.presentation.view.orderstatusscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.presentation.view.orderdetailscreen.OrderDetailsActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersActivity : BaseActivity<ActivityOrdersBinding>() {
    private val ordersViewModel: OrdersViewModel by viewModels()
    private var pageNumber = 0
    private var isLoading = true
    private var pastVisibleItem: Int = 0
    var visibleItemsCount: Int = 0
    var totalItemsCount: Int = 0
    var previousTotal = 0
    private var threshold = 2
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
                        if (resource.data!!.status){
                            if (resource.data.status_code == 200){
                                binding.progressActivity.showContent()
                                mAdapter.submitList(resource.data.data?.data)
                            }else{
                                binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "", resource.data.message)
                            }
                        }else{
                            binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "لا يوجد طلبات", resource.data.message)
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

//        binding.ordersRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                visibleItemsCount = mLayoutManager.childCount
//                totalItemsCount = mLayoutManager.itemCount
//                pastVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
//
//                if (dy > 0) {
//                    if (isLoading) {
//                        if (totalItemsCount > previousTotal) { // da el stopping condition, el condition da 3shan a turn on el isloading lma el items tzed m3na kda en fe data gt tany
//                            isLoading = false
//                            previousTotal = totalItemsCount
//                        }
//                    }
//
//                    if (!isLoading && (totalItemsCount - visibleItemsCount) <= (pastVisibleItem + threshold)) { // el data gt w el unseen items b2t <= el threshold
//                        pageNumber += 1
//                        isLoading = true
//                        loadMoreData()
//                    }
//                }
//            }
//        })
    }

    private fun loadMoreData() {
        ordersViewModel.getOrders(Constants.userNumber, 10, pageNumber)
    }
}