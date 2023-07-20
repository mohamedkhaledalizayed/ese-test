package com.neqabty.healthcare.mypackages.subscriptiondetails.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerLayoutBinding
import com.neqabty.healthcare.mypackages.packages.domain.entity.FollowerEntity


class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val items: MutableList<FollowerEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: FollowerLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.follower_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.followerName.text = item.fullName
        viewHolder.binding.nationalId.text = item.nationalId
        viewHolder.binding.deleteFollower.setOnClickListener {
            onItemClickListener?.setOnDeleteFollowerListener(item.subscriberId, item.id)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<FollowerEntity>) {
        clear()
        newItems.let {
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
            fun setOnDeleteFollowerListener(subscriberId: String, followerId: Int)
    }

    class ViewHolder(val binding: FollowerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}