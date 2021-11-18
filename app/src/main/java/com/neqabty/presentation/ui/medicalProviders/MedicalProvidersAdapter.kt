package com.neqabty.presentation.ui.medicalProviders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProviderItemBinding
import com.neqabty.presentation.entities.MedicalDirectoryProviderUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter
import com.neqabty.ui.presentation.common.DataBoundViewHolder

class MedicalProvidersAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MedicalDirectoryProviderUI) -> Unit)?
) : DataBoundListAdapter<MedicalDirectoryProviderUI, MedicalProviderItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalDirectoryProviderUI>() {
            override fun areItemsTheSame(oldItem: MedicalDirectoryProviderUI, newItem: MedicalDirectoryProviderUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MedicalDirectoryProviderUI, newItem: MedicalDirectoryProviderUI): Boolean {
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

    override fun bind(binding: MedicalProviderItemBinding, item: MedicalDirectoryProviderUI) {
        binding.provider = item
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<MedicalProviderItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        if(position %2 == 0)
            holder.binding.clRoot.setBackgroundResource(R.drawable.provider_rounded_green_bg)
        else
            holder.binding.clRoot.setBackgroundResource(R.drawable.provider_rounded_blue_bg)
    }
}
