package com.neqabty.healthcare.chefaa.home.presentation.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.databinding.OrderLayoutItemBinding
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
        val order = items[position]

        viewHolder.binding.orderStatus.text = order.orderStatus.titleAr
        viewHolder.binding.orderNumber.text = order.chefaaOrderNumber
        viewHolder.binding.orderDate.text = order.createdAt

        when (order.status) {
            1 -> {
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_new_bg)
                viewHolder.binding.total.visibility = View.GONE
                viewHolder.binding.arrowIcon.visibility = View.GONE

                viewHolder.binding.root.setOnClickListener {
                    onItemClickListener?.setOnCallClickListener()
                }
            }
            2 -> {
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)
            }
            else -> {
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)


                viewHolder.binding.root.setOnClickListener {
                    onItemClickListener?.setOnItemClickListener(order)
                }
            }
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
        fun setOnItemClickListener(orderEntity: OrderEntity)
        fun setOnCallClickListener()
    }

    class ViewHolder(val binding: OrderLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}