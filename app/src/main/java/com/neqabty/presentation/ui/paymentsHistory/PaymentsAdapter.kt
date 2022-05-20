package com.neqabty.presentation.ui.paymentsHistory

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PaymentsHistoryItemBinding
import com.neqabty.presentation.entities.PaymentHistoryUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class PaymentsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((PaymentHistoryUI) -> Unit)?
) : DataBoundListAdapter<PaymentHistoryUI, PaymentsHistoryItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<PaymentHistoryUI>() {
            override fun areItemsTheSame(oldItem: PaymentHistoryUI, newItem: PaymentHistoryUI): Boolean {
                return oldItem.reference == newItem.reference
            }

            override fun areContentsTheSame(oldItem: PaymentHistoryUI, newItem: PaymentHistoryUI): Boolean {
                return oldItem.reference == newItem.reference
            }
        }
) {

    override fun createBinding(parent: ViewGroup): PaymentsHistoryItemBinding {
        val binding = DataBindingUtil
                .inflate<PaymentsHistoryItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.payments_history_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        return binding
    }

    override fun bind(binding: PaymentsHistoryItemBinding, item: PaymentHistoryUI) {
        binding.tvName.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_name, item.product))
        binding.tvAmount.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_amount, item.amount))
        binding.tvDate.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_date, item.createdAt))
        binding.tvReference.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_reference, item.reference))
        binding.tvStatus.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_status, item.status))
        binding.tvGateway.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_gateway, item.gateway))
        binding.tvCode.text = Html.fromHtml(binding.root.context.getString(R.string.payment_history_code, item.code))
        binding.tvCode.visibility = if(item.code.isNotEmpty()) View.VISIBLE else View.GONE
    }
}
