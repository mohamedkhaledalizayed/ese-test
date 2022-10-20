package com.neqabty.healthcare.sustainablehealth.mypackages.presentation

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerItemLayoutBinding
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.FollowerEntity
import kotlin.collections.ArrayList


class FollowerAdapter: RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private val items: MutableList<FollowerEntity> = ArrayList()
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


        viewHolder.binding.followerName.text = follower.fullName
        viewHolder.binding.nationalId.text = follower.nationalId
        viewHolder.binding.relation.text = follower.relation.relation
        if (!follower.image.isNullOrEmpty()){
            val decodedString: ByteArray = Base64.decode(follower.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            viewHolder.binding.followerImage.setImageBitmap(decodedByte)
        }

        viewHolder.binding.delete.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(follower.subscriberId, follower.id)
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
            fun setOnItemClickListener(subscriberId: String, followerId: Int)
    }

    class ViewHolder(val binding: FollowerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}