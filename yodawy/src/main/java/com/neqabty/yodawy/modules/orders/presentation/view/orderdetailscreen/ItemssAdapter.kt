package com.neqabty.yodawy.modules.orders.presentation.view.orderdetailscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.ProductLayoutItemBinding
import com.neqabty.yodawy.modules.orders.domain.entity.OrderItemEntity


class ItemssAdapter: RecyclerView.Adapter<ItemssAdapter.ViewHolder>() {

    private val items: MutableList<OrderItemEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ProductLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.product_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }
        viewHolder.binding.imageProgress.visibility = View.GONE
        viewHolder.binding.medicationTitle.text = item.drugName
        viewHolder.binding.medicationQuantity.text = "العدد : ${item.quantity}"
        viewHolder.binding.medicationPrice.text = "${item.price} جنيه"

        if (item.removed){
            viewHolder.binding.status.visibility = View.VISIBLE
            viewHolder.binding.status.text = "محذوف"
        }else{
            viewHolder.binding.view.visibility = View.GONE
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<OrderItemEntity>) {
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
        fun setOnItemClickListener(itemId: Int)
    }

    class ViewHolder(val binding: ProductLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}