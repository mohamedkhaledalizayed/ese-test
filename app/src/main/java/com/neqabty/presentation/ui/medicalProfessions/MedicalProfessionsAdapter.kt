package com.neqabty.presentation.ui.medicalProfessions

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProfessionItemBinding
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalProfessionsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((SpecializationUI) -> Unit)?
) : DataBoundListAdapter<SpecializationUI, MedicalProfessionItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<SpecializationUI>() {
            override fun areItemsTheSame(oldItem: SpecializationUI, newItem: SpecializationUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SpecializationUI, newItem: SpecializationUI): Boolean {
                return oldItem.profession == newItem.profession
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalProfessionItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalProfessionItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_profession_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.profession?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MedicalProfessionItemBinding, item: SpecializationUI) {
        binding.profession = item
    }
}
