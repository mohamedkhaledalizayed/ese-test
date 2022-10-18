package com.neqabty.presentation.ui.syndicateServicesDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.RenewPaymentItemBinding
import com.neqabty.databinding.SyndicateServicesPaymentItemBinding
import com.neqabty.presentation.entities.SyndicateServicesPaymentUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class SyndicateServicesPaymentItemsAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((SyndicateServicesPaymentUI.PaymentDetailsItem) -> Unit)?
) : DataBoundListAdapter<SyndicateServicesPaymentUI.PaymentDetailsItem, SyndicateServicesPaymentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<SyndicateServicesPaymentUI.PaymentDetailsItem>() {
            override fun areItemsTheSame(oldItem: SyndicateServicesPaymentUI.PaymentDetailsItem, newItem: SyndicateServicesPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.totalAmount == newItem.totalAmount
            }

            override fun areContentsTheSame(oldItem: SyndicateServicesPaymentUI.PaymentDetailsItem, newItem: SyndicateServicesPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.totalAmount == newItem.totalAmount
            }
        }
) {

    override fun createBinding(parent: ViewGroup): SyndicateServicesPaymentItemBinding {
        val binding = DataBindingUtil
                .inflate<SyndicateServicesPaymentItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.syndicate_services_payment_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.syndicateServicesPaymentUI?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: SyndicateServicesPaymentItemBinding, item: SyndicateServicesPaymentUI.PaymentDetailsItem) {
        binding.syndicateServicesPaymentUI = item
    }
}
