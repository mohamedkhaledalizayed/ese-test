package com.neqabty.presentation.ui.medicalRenewUpdate

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.FollowerUpdateItemBinding
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter
import kotlinx.android.synthetic.main.follower_update_item.view.*

class FollowersUpdateAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val removeCallback: ((MedicalRenewalUI.FollowerItem) -> Unit)?,
    private val editCallback: ((MedicalRenewalUI.FollowerItem) -> Unit)?
) : DataBoundListAdapter<MedicalRenewalUI.FollowerItem, FollowerUpdateItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MedicalRenewalUI.FollowerItem>() {
            override fun areItemsTheSame(oldItem: MedicalRenewalUI.FollowerItem, newItem: MedicalRenewalUI.FollowerItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MedicalRenewalUI.FollowerItem, newItem: MedicalRenewalUI.FollowerItem): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): FollowerUpdateItemBinding {
        val binding = DataBindingUtil
                .inflate<FollowerUpdateItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.follower_update_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.follower?.let {
                editCallback?.invoke(it)
            }
        }
        binding.root.bClose.setOnClickListener {
            binding.follower?.let {
                removeCallback?.invoke(it)
            }
        }
        binding.root.bEdit.setOnClickListener {
            binding.follower?.let {
                editCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: FollowerUpdateItemBinding, item: MedicalRenewalUI.FollowerItem) {
        binding.follower = item
    }
}
