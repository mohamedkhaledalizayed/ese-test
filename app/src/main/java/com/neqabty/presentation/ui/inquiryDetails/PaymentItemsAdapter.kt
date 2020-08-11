package com.neqabty.presentation.ui.inquiryDetails

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PaymentItemBinding
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class PaymentItemsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MemberUI.PaymentItem) -> Unit)?
) : DataBoundListAdapter<MemberUI.PaymentItem, PaymentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MemberUI.PaymentItem>() {
            override fun areItemsTheSame(oldItem: MemberUI.PaymentItem, newItem: MemberUI.PaymentItem): Boolean {
                return oldItem.totalPrice == newItem.totalPrice
            }

            override fun areContentsTheSame(oldItem: MemberUI.PaymentItem, newItem: MemberUI.PaymentItem): Boolean {
                return oldItem.totalPrice == newItem.totalPrice
            }
        }
) {

    override fun createBinding(parent: ViewGroup): PaymentItemBinding {
        val binding = DataBindingUtil
                .inflate<PaymentItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.payment_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.payments?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: PaymentItemBinding, item: MemberUI.PaymentItem) {
        binding.payments = item
    }
}
