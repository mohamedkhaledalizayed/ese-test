package com.neqabty.presentation.ui.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SyndicateBranchItemBinding
import com.neqabty.presentation.entities.SyndicateBranchUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class BranchesAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((SyndicateBranchUI) -> Unit)?
) : DataBoundListAdapter<SyndicateBranchUI, SyndicateBranchItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<SyndicateBranchUI>() {
            override fun areItemsTheSame(oldItem: SyndicateBranchUI, newItem: SyndicateBranchUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SyndicateBranchUI, newItem: SyndicateBranchUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): SyndicateBranchItemBinding {
        val binding = DataBindingUtil
                .inflate<SyndicateBranchItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.syndicate_branch_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.branch?.let {
                callback?.invoke(it)
            }
        }

//        binding.ivLogo.layoutParams.height = DisplayMetrics.width / 6
//        binding.ivLogo.requestLayout()

        return binding
    }

    override fun bind(binding: SyndicateBranchItemBinding, item: SyndicateBranchUI) {
        binding.branch = item
    }
}
