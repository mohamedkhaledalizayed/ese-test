package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ProductItemBinding
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.ItemsEntity


class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items: MutableList<ItemsEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ProductItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.product_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.medicationTitle.text = item.name
        viewHolder.binding.medicationPrice.text = "السعر : ${item.price} جنيه"
        viewHolder.binding.medicationQuantity.text = "العدد : ${item.quantity}"
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<ItemsEntity>) {
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
        fun setOnItemClickListener(itemId: Int)
    }

    class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}