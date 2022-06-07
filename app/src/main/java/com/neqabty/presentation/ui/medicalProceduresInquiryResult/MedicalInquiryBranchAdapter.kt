package com.neqabty.presentation.ui.medicalProceduresInquiryResult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalInquiryProcedureItemBinding
import com.neqabty.presentation.entities.MedicalBranchProcedureUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalInquiryBranchAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val appExecutors: AppExecutors,
    private val callback: ((MedicalBranchProcedureUI) -> Unit)?
) : DataBoundListAdapter<MedicalBranchProcedureUI, MedicalInquiryProcedureItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalBranchProcedureUI>() {
            override fun areItemsTheSame(oldItem: MedicalBranchProcedureUI, newItem: MedicalBranchProcedureUI): Boolean {
                return oldItem.profileId == newItem.profileId
            }

            override fun areContentsTheSame(oldItem: MedicalBranchProcedureUI, newItem: MedicalBranchProcedureUI): Boolean {
                return oldItem.profileId == newItem.profileId
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalInquiryProcedureItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalInquiryProcedureItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_inquiry_procedure_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.procedure?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MedicalInquiryProcedureItemBinding, item: MedicalBranchProcedureUI) {
        binding.procedure = item
    }
}
