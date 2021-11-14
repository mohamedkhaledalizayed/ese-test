package com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.utils.AppUtils
import com.neqabty.yodawy.databinding.OrderLayoutItemBinding
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OrdersAdapter: RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

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
        viewHolder.binding.orderStatus.text = "حالة الطلب: ${item.currentStatus}"
        viewHolder.binding.orderDate.text = "تاريخ الطلب: ${AppUtils().dateFormat(item.creationDate)}"
        viewHolder.binding.orderDetails.text = "رقم الطلب: ${item.orderNumber}"
        viewHolder.binding.total.text = "السعر: ${item.orderPrice} جنيه"


        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
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
            fun setOnItemClickListener(itemId: Int)
    }

    class ViewHolder(val binding: OrderLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}