package com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.databinding.OrderLayoutItemBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderClientEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
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
        val item = items[position]
        viewHolder.binding.orderStatus.text = "حالة الطلب: ${item.orderStatus.titleAr}"
        viewHolder.binding.orderDate.text = "تاريخ الطلب: ${AppUtils().dateFormat(item.createdAt)}"
        viewHolder.binding.orderDetails.text = "رقم الطلب: ${item.id}"
        viewHolder.binding.deliveryFees.text = "مصاريف الشحن: ${item.deliveryFees} جنيه"
        viewHolder.binding.total.text = "السعر: ${item.price} جنيه"


        if (position == itemCount - 1) {
            viewHolder.binding.view.visibility = View.GONE
        } else {
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        viewHolder.binding.layoutItem.setOnClickListener {
            if (item.orderStatus.id != 1 && item.orderStatus.id != 4)
                onItemClickListener?.setOnItemClickListener(item)
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
        fun setOnItemClickListener(item: OrderEntity)
    }

    class ViewHolder(val binding: OrderLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}