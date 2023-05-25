package com.neqabty.healthcare.commen.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.OurNewsItemBinding
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.databinding.NotificationItemBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


open class NotificationsAdapter: RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: NotificationItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.notification_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = 10

    fun submitList(newItems: List<String>?) {
        clear()
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
            fun setOnItemClickListener(item: String)
    }

    class ViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}