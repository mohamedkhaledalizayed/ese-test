package com.neqabty.healthcare.sustainablehealth.search.presentation.view.searchresult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.SearchItemBinding
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items: MutableList<ProvidersEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: SearchItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.search_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        if (!item.image.isNullOrEmpty()){
            Picasso.get().load(item.image).placeholder(R.drawable.user).into(viewHolder.binding.itemImage)
        }

        viewHolder.binding.itemName.text = item.name
        viewHolder.binding.government.text = "${item.governorate.governorateAr}, ${item.area?.areaName}"

        viewHolder.binding.itemLayout.setOnClickListener { onItemClickListener?.setOnItemClickListener(item) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<ProvidersEntity>?) {
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
            fun setOnItemClickListener(item: ProvidersEntity)
    }

    class ViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}