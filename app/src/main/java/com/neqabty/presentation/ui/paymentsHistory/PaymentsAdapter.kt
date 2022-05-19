package com.neqabty.presentation.ui.paymentsHistory

import android.view.LayoutInflater
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
        (binding.root.context.getString(R.string.amount) + item.amount).also { binding.tvAmount.text = it }
        (binding.root.context.getString(R.string.reference) + item.reference).also { binding.tvReference.text = it }
        (binding.root.context.getString(R.string.code) + item.code).also { binding.tvCode.text = it }
        (binding.root.context.getString(R.string.status) + item.status).also { binding.tvStatus.text = it }
        (binding.root.context.getString(R.string.gateway) + item.gateway).also { binding.tvGateway.text = it }
    }
}
