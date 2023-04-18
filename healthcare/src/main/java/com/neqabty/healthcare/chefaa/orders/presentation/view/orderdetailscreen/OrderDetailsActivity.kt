package com.neqabty.healthcare.chefaa.orders.presentation.view.orderdetailscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.databinding.ActivityOrderDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {

    private val orderViewModel: GetOrderViewModel by viewModels()
    private val mAdapter = ItemsAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    private lateinit var dialog: AlertDialog
//    private var navigation = false
    private var prescriptionsImages: MutableList<String> = ArrayList()
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.order_details)

        val orderId = intent.getParcelableExtra<OrderEntity>("orderId")
//        navigation = intent.getBooleanExtra("navigation", false)
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()
        prescriptionsAdapter = PrescriptionsAdapter(this)

        binding.orderStatusValue.text = orderId?.orderStatus?.titleAr
        binding.orderNumberValue.text = orderId?.chefaaOrderNumber.toString()
        binding.dateValue.text = AppUtils().dateFormat(orderId?.createdAt!!)
        binding.totalPaymentBeforeDiscount.text = "${orderId?.priceBeforeDiscount}  جنيه "
        binding.totalPaymentAfterDiscount.text = "${orderId?.price}  جنيه "
        binding.deliveryFees.text = "${orderId?.deliveryFees}  جنيه "
        binding.totalValue.text = "${(orderId?.price)}  جنيه "
        mAdapter.submitList(orderId?.items)
        binding.productsRecycler.visibility = View.VISIBLE
        binding.productsRecycler.adapter = mAdapter


    }
}