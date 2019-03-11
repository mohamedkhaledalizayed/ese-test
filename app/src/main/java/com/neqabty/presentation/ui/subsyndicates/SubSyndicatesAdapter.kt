package com.neqabty.presentation.ui.subsyndicates

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SubsyndicateItemBinding
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class SubSyndicatesAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((SyndicateUI) -> Unit)?
) : DataBoundListAdapter<SyndicateUI, SubsyndicateItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<SyndicateUI>() {
            override fun areItemsTheSame(oldItem: SyndicateUI, newItem: SyndicateUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SyndicateUI, newItem: SyndicateUI): Boolean {
                return oldItem.address == newItem.address
            }
        }
) {

    override fun createBinding(parent: ViewGroup): SubsyndicateItemBinding {
        val binding = DataBindingUtil
                .inflate<SubsyndicateItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.subsyndicate_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.subSyndicate?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: SubsyndicateItemBinding, item: SyndicateUI) {
        binding.subSyndicate = item
    }
}
