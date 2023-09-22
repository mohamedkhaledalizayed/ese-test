package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails


import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyMartOrderDetailsBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PharmacyMartOrderDetailsActivity : BaseActivity<ActivityPharmacyMartOrderDetailsBinding>() {

    private val viewModel: OrdersViewModel by viewModels()
    override fun getViewBinding() = ActivityPharmacyMartOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.getOrder(intent.getStringExtra("orderId") ?: "")
        viewModel.order.observe(this){
            when(it.status){
                Status.LOADING ->{

                }
                Status.SUCCESS ->{

                }
                Status.ERROR ->{

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