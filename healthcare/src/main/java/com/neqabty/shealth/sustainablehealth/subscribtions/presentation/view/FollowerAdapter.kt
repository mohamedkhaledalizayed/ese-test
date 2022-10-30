package com.neqabty.shealth.sustainablehealth.subscribtions.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.shealth.R
import com.neqabty.shealth.databinding.FollowerItemBinding
import com.neqabty.shealth.sustainablehealth.subscribtions.data.model.Followers
import kotlin.collections.ArrayList


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private val items: MutableList<Followers> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: FollowerItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.follower_item, parent, false)

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

        if (position == (items.size - 1)){
            viewHolder.binding.view.visibility == View.GONE
        }
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

    class ViewHolder(val binding: FollowerItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}