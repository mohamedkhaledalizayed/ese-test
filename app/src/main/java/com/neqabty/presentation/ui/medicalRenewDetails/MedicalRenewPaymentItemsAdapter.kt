package com.neqabty.presentation.ui.medicalRenewDetails

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewPaymentItemBinding
import com.neqabty.databinding.PaymentItemBinding
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalRenewPaymentItemsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MedicalRenewalPaymentUI) -> Unit)?
) : DataBoundListAdapter<MedicalRenewalPaymentUI, MedicalRenewPaymentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalRenewalPaymentUI>() {
            override fun areItemsTheSame(oldItem: MedicalRenewalPaymentUI, newItem: MedicalRenewalPaymentUI): Boolean {
                return oldItem.requestID == newItem.requestID
            }

            override fun areContentsTheSame(oldItem: MedicalRenewalPaymentUI, newItem: MedicalRenewalPaymentUI): Boolean {
                return oldItem.requestID == newItem.requestID
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
            binding.medicalRenewalPayment?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: MedicalRenewPaymentItemBinding, item: MedicalRenewalPaymentUI) {
        binding.medicalRenewalPayment = item
    }
}
