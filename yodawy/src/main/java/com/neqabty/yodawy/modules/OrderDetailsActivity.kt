package com.neqabty.yodawy.modules

import android.os.Bundle
import android.util.Log
import android.view.View
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.AppUtils
import com.neqabty.yodawy.databinding.ActivityOrderDetailsBinding
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity

class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {
    private val mAdapter = ItemssAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.order_details)


        val order = intent.extras?.getParcelable<OrderEntity>("order")!!

        binding.addressType.text = order.address.addressName
        binding.addressDetails.text = order.address.addressName
        binding.orderNumberValue.text = order.orderNumber
        binding.dateValue.text = AppUtils().dateFormat(order.creationDate)
        binding.totalPayment.text = "${order.orderPrice} جنيه"
        if (order.items.isNotEmpty()){
            binding.productsRecycler.adapter = mAdapter
            mAdapter.submitList(order.items)
        }else{
            binding.productsRecycler.visibility = View.GONE
        }


        if (order.prescriptionImageEntities!!.isNotEmpty()){
            prescriptionsAdapter = PrescriptionsAdapter(this)
            binding.photosRecycler.adapter = prescriptionsAdapter
            prescriptionsAdapter.submitList(order.prescriptionImageEntities)
        }else{
            binding.photosRecycler.visibility = View.GONE
        }
    }
}