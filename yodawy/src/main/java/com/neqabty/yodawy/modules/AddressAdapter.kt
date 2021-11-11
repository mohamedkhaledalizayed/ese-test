package com.neqabty.yodawy.modules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.AddressLayoutItemBinding


class AddressAdapter: RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: AddressLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.address_layout_item, parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(position)
        }
    }

    override fun getItemCount() = 3

    fun submitList(newItems: List<String>?) {
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

    interface OnItemClickListener { fun setOnItemClickListener(itemId: Int) }

    class ViewHolder(val binding: AddressLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}