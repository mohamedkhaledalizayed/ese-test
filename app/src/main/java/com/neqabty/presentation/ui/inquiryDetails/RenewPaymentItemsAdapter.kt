package com.neqabty.presentation.ui.inquiryDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.RenewPaymentItemBinding
import com.neqabty.presentation.entities.RenewalPaymentUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class RenewPaymentItemsAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((RenewalPaymentUI.PaymentDetailsItem) -> Unit)?
) : DataBoundListAdapter<RenewalPaymentUI.PaymentDetailsItem, RenewPaymentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<RenewalPaymentUI.PaymentDetailsItem>() {
            override fun areItemsTheSame(oldItem: RenewalPaymentUI.PaymentDetailsItem, newItem: RenewalPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.totalAmount == newItem.totalAmount
            }

            override fun areContentsTheSame(oldItem: RenewalPaymentUI.PaymentDetailsItem, newItem: RenewalPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.totalAmount == newItem.totalAmount
            }
        }
) {

    override fun createBinding(parent: ViewGroup): RenewPaymentItemBinding {
        val binding = DataBindingUtil
                .inflate<RenewPaymentItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.renew_payment_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.renewalPaymentDetailsItem?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: RenewPaymentItemBinding, item: RenewalPaymentUI.PaymentDetailsItem) {
        binding.renewalPaymentDetailsItem = item
    }
}
