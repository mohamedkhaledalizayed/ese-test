package com.neqabty.presentation.ui.medicalProviders

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProviderItemBinding
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class MedicalProvidersAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((ProviderUI) -> Unit)?
) : DataBoundListAdapter<ProviderUI, MedicalProviderItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<ProviderUI>() {
            override fun areItemsTheSame(oldItem: ProviderUI, newItem: ProviderUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProviderUI, newItem: ProviderUI): Boolean {
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
        return binding
    }

    override fun bind(binding: MedicalProviderItemBinding, item: ProviderUI) {
        binding.provider = item
    }
}
