package com.neqabty.presentation.ui.medicalRenew

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.FollowerItemBinding
import com.neqabty.databinding.PaymentItemBinding
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class FollowersAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((MedicalRenewalUI.FollowerItem) -> Unit)?
) : DataBoundListAdapter<MedicalRenewalUI.FollowerItem, FollowerItemBinding>(
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

    override fun createBinding(parent: ViewGroup): FollowerItemBinding {
        val binding = DataBindingUtil
                .inflate<FollowerItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.follower_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.follower?.let {
                callback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: FollowerItemBinding, item: MedicalRenewalUI.FollowerItem) {
        binding.follower = item
    }
}
