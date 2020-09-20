package com.neqabty.presentation.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationItemBinding
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.util.DisplayMetrics
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
                return oldItem.isRead == newItem.isRead
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
        binding.ivNotification.layoutParams.width = DisplayMetrics.width / 6
        binding.ivNotification.layoutParams.height = DisplayMetrics.width / 6
        return binding
    }

    override fun bind(binding: NotificationItemBinding, item: NotificationUI) {
        binding.notification = item
    }
}
