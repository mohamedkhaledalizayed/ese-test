package com.neqabty.healthcare.pharmacymart.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.OrderLayoutBinding
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrderEntity
import java.util.*


class PharmacyMartOrdersAdapter : RecyclerView.Adapter<PharmacyMartOrdersAdapter.ViewHolder>() {

    private val items: MutableList<OrderEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: OrderLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.order_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = items[position]

        viewHolder.binding.orderStatus.text = order.order_status.title_ar
        viewHolder.binding.orderNumber.text = order.order_number
        viewHolder.binding.orderDate.text = order.created_at

        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(order)
        }

        when (order.status) {
            1 -> {//New
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_new_bg)
            }
            2 -> {//Accepted
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_accepted_bg)
            }
            3 -> {//In Progress
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_inprogress_bg)
            }
            4 -> {//Delivered
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)
            }
            5 -> {//Canceled
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_canceled_bg)
            }
            else -> {
                viewHolder.binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)
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
    }

    class ViewHolder(val binding: OrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}