package com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerItemBinding
import com.neqabty.healthcare.databinding.FollowerLayoutBinding
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.Followers
import kotlin.collections.ArrayList


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private val items: MutableList<Followers> = ArrayList()
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
        val follower = items[position]

        viewHolder.binding.followerName.text = follower.name
        viewHolder.binding.nationalId.text = follower.national_id


        viewHolder.binding.deleteFollower.setOnClickListener { onItemClickListener?.setOnDeleteClickListener(position) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Followers>) {
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
        fun setOnDeleteClickListener(position: Int)
    }

    class ViewHolder(val binding: FollowerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}