package com.neqabty.chefaa.modules.home.presentation.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.databinding.OrderLayoutItemBinding
import com.neqabty.chefaa.modules.home.domain.entities.OrderEntity
import java.util.*


class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private val items: MutableList<OrderEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: OrderLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.order_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener()
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<OrderEntity>?) {
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
        fun setOnItemClickListener()
    }

    class ViewHolder(val binding: OrderLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}