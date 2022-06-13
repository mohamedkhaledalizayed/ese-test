package com.neqabty.healthcare.modules.register.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerItemLayoutBinding
import com.neqabty.healthcare.modules.register.presentation.model.Follower
import kotlin.collections.ArrayList


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private val items: MutableList<Follower> = ArrayList()
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
        viewHolder.binding.nationalId.text = follower.nationalId
        viewHolder.binding.relation.text = follower.relation
        viewHolder.binding.followerImage.setImageURI(follower.image)
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Follower>) {
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