package com.neqabty.presentation.ui.notifications

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationItemBinding
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class NotificationsAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((NotificationUI) -> Unit)?
) : DataBoundListAdapter<NotificationUI, NotificationItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<NotificationUI>() {
            override fun areItemsTheSame(oldItem: NotificationUI, newItem: NotificationUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NotificationUI, newItem: NotificationUI): Boolean {
                return oldItem.doc1 == newItem.doc1
            }
        }
) {

    override fun createBinding(parent: ViewGroup): NotificationItemBinding {
        val binding = DataBindingUtil
                .inflate<NotificationItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.notification_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.notification?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: NotificationItemBinding, item: NotificationUI) {
        binding.notification = item
    }
}
