package com.neqabty.presentation.ui.medicalProviders

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProviderItemBinding
import com.neqabty.presentation.entities.MedicalProviderUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalProvidersAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((MedicalProviderUI) -> Unit)?
) : DataBoundListAdapter<MedicalProviderUI, MedicalProviderItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalProviderUI>() {
            override fun areItemsTheSame(oldItem: MedicalProviderUI, newItem: MedicalProviderUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MedicalProviderUI, newItem: MedicalProviderUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): MedicalProviderItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalProviderItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_provider_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.provider?.let {
                callback?.invoke(it)
            }
        }

        val layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width /2,DisplayMetrics.width /2)
        binding.root.layoutParams = layoutParams
        return binding
    }

    override fun bind(binding: MedicalProviderItemBinding, item: MedicalProviderUI) {
        binding.provider = item
    }
}
