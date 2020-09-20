package com.neqabty.presentation.ui.favorites

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalFavoriteItemBinding
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class FavoritesAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((ProviderUI) -> Unit)?,
    private val removeCallback: ((ProviderUI) -> Unit)?
) : DataBoundListAdapter<ProviderUI, MedicalFavoriteItemBinding>(
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

    override fun createBinding(parent: ViewGroup): MedicalFavoriteItemBinding {
        val binding = DataBindingUtil
                .inflate<MedicalFavoriteItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.medical_favorite_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.provider?.let {
                callback?.invoke(it)
            }
        }
        binding.ivRemove.setOnClickListener {
            binding.provider?.let {
                removeCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MedicalFavoriteItemBinding, item: ProviderUI) {
        binding.provider = item
    }
}
