package com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.databinding.ActivityOrderDetailsBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {

    private val mAdapter = ItemsAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.order_details)

        val orderId = intent.getParcelableExtra<OrderEntity>("orderId")
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