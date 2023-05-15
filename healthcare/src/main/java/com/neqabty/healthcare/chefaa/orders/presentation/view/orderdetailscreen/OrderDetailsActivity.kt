package com.neqabty.healthcare.chefaa.orders.presentation.view.orderdetailscreen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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

    private val mAdapter = ItemsAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.headerContainer.setOnClickListener { finish() }
        val orderId = intent.getParcelableExtra<OrderEntity>("orderId")
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()
        prescriptionsAdapter = PrescriptionsAdapter(this)
        binding.productRecyclerView.adapter = mAdapter
        mAdapter.submitList(orderId?.items!!)
        binding.completeBtn.setOnClickListener { finish() }


    }
}