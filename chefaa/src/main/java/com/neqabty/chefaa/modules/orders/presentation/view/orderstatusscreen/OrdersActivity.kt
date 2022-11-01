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
import com.neqabty.chefaa.modules.orders.domain.entities.OrderClientEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen.OrderDetailsActivity
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
                        if (resource.data!!.isEmpty()){
                            if (mAdapter.itemCount == 0){
                                binding.progressActivity.showEmpty(R.drawable.ic_undraw_empty_xct9, "لا يوجد طلبات", "لم تقم باى طلب من قبل")
                            }else{
                                binding.progressActivity.showContent()
                            }
                        }else{
                            binding.progressActivity.showContent()
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressActivity.showEmpty(R.drawable.ic_undraw_access_denied_6w73, "خطا", resource.message)
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