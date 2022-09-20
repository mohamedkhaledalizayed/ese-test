package com.neqabty.healthcare.modules.subscribtions.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerItemLayoutBinding
import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import com.neqabty.healthcare.modules.subscribtions.presentation.view.model.Follower
import kotlin.collections.ArrayList


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private val items: MutableList<Followers> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: FollowerItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.follower_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val follower = items[position]

        viewHolder.binding.followerName.text = follower.name
        viewHolder.binding.nationalId.text = follower.national_id
        viewHolder.binding.relation.text = follower.relation
        viewHolder.binding.followerImage.setImageURI(follower.imageUri)

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
            fun setOnItemClickListener(item: String)
    }

    class ViewHolder(val binding: FollowerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}