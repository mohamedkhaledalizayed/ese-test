package com.neqabty.presentation.ui.phones

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PhoneItemBinding
import com.neqabty.presentation.util.call
import com.neqabty.ui.presentation.common.DataBoundListAdapter
import com.neqabty.ui.presentation.common.DataBoundViewHolder
import kotlinx.android.synthetic.main.phone_item.view.*

class PhonesAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : DataBoundListAdapter<String, PhoneItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
) {

    override fun createBinding(parent: ViewGroup): PhoneItemBinding {
        val binding = DataBindingUtil
                .inflate<PhoneItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.phone_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            it.tvPhone.call(binding.tvPhone.text.toString(), it.context)
        }
        return binding
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<PhoneItemBinding>, position: Int) {
        if (position == itemCount - 1) {
            holder.binding.view.visibility = View.GONE
        }
        holder.binding.tvPhone.setText(getItem(position))
    }

    override fun bind(binding: PhoneItemBinding, item: String) {
        binding.phone = item
    }
}
