package com.neqabty.presentation.ui.medicalRenewDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewPaymentItemBinding
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalRenewPaymentItemsAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((MedicalRenewalPaymentUI.PaymentDetailsItem) -> Unit)?
) : DataBoundListAdapter<MedicalRenewalPaymentUI.PaymentDetailsItem, MedicalRenewPaymentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalRenewalPaymentUI.PaymentDetailsItem>() {
            override fun areItemsTheSame(oldItem: MedicalRenewalPaymentUI.PaymentDetailsItem, newItem: MedicalRenewalPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MedicalRenewalPaymentUI.PaymentDetailsItem, newItem: MedicalRenewalPaymentUI.PaymentDetailsItem): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalRenewPaymentItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalRenewPaymentItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_renew_payment_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.medicalRenewalPaymentDetailsItem?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: MedicalRenewPaymentItemBinding, item: MedicalRenewalPaymentUI.PaymentDetailsItem) {
        binding.medicalRenewalPaymentDetailsItem = item
    }
}
