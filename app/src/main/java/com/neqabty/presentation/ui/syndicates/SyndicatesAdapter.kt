package com.neqabty.presentation.ui.syndicates

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SyndicateItemBinding
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class SyndicatesAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((SyndicateUI) -> Unit)?
) : DataBoundListAdapter<SyndicateUI, SyndicateItemBinding>(
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

    override fun createBinding(parent: ViewGroup): SyndicateItemBinding {
        val binding = DataBindingUtil
                .inflate<SyndicateItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.syndicate_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.syndicate?.let {
                callback?.invoke(it)
            }
        }

        val layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width /3,DisplayMetrics.width /3)
        binding.root.layoutParams = layoutParams
        return binding
    }

    override fun bind(binding: SyndicateItemBinding, item: SyndicateUI) {
        binding.syndicate = item
    }
}
