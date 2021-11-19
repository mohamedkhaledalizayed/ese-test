package com.neqabty.yodawy.modules

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.mobileNumber
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.AppUtils
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivityOrderDetailsBinding
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {

    private val orderViewModel: GetOrderViewModel by viewModels()
    private val mAdapter = ItemssAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.order_details)

        val orderId = intent.getStringExtra("orderId")
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        orderViewModel.getSpecificOrder(mobileNumber, "$orderId")

        orderViewModel.order.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        binding.addressType.text = resource.data?.address?.addressName
                        binding.addressDetails.text = resource.data?.address?.address
                        binding.orderNumberValue.text = resource.data?.orderNumber
                        binding.dateValue.text = AppUtils().dateFormat(resource.data?.creationDate!!)
                        binding.totalPayment.text = "${resource.data?.orderPrice} جنيه"
                        if (resource.data.items.isNotEmpty()){
                            binding.productsRecycler.adapter = mAdapter
                            mAdapter.submitList(resource.data.items)
                        }else{
                            binding.productsRecycler.visibility = View.GONE
                        }


                        if (resource.data.prescriptionImageEntities!!.isNotEmpty()){
                            prescriptionsAdapter = PrescriptionsAdapter(this)
                            binding.photosRecycler.adapter = prescriptionsAdapter
                            prescriptionsAdapter.submitList(resource.data.prescriptionImageEntities)
                        }else{
                            binding.photosRecycler.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }

    }
}