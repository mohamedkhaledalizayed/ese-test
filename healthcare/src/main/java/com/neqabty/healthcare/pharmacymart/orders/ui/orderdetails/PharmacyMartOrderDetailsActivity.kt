package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyMartOrderDetailsBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PharmacyMartOrderDetailsActivity : BaseActivity<ActivityPharmacyMartOrderDetailsBinding>() {


    private val mAdapter = ItemsAdapter()
    private val viewModel: OrdersViewModel by viewModels()
    override fun getViewBinding() = ActivityPharmacyMartOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.productRecyclerView.adapter = mAdapter
        viewModel.getOrder(intent.getStringExtra("orderId") ?: "")
        viewModel.order.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    binding.progressCircular.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                    mAdapter.submitList(it.data!!.data[0].items)
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }

//        viewModel.cancelOrder(intent.getStringExtra("orderId") ?: "", "")
        viewModel.cancellationStatus.observe(this){
            when(it.status){
                Status.LOADING ->{

                }
                Status.SUCCESS ->{

                }
                Status.ERROR ->{

                }
            }
        }

//        viewModel.confirmOrder(intent.getStringExtra("orderId") ?: "")
        viewModel.confirmationStatus.observe(this){
            when(it.status){
                Status.LOADING ->{

                }
                Status.SUCCESS ->{

                }
                Status.ERROR ->{

                }
            }
        }
    }
}