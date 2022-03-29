package com.neqabty.superneqabty.paymentdetails.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.databinding.ServiceLayoutItemBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity


class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items: MutableList<Detail> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ServiceLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.service_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]

        viewHolder.binding.serviceValue.text = item.name
        viewHolder.binding.countValue.text = item.quantity.toString()
        viewHolder.binding.priceValue.text = item.price.toString()
        viewHolder.binding.totalValue.text = item.total.toString()

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Detail>?) {
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
            fun setOnItemClickListener(item: NewsEntity)
    }

    class ViewHolder(val binding: ServiceLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}