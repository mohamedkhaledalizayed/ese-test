package com.neqabty.yodawy.modules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.CartLayoutItemBinding


class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val items: MutableList<Medication> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CartLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.cart_layout_item, parent, false)

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

        viewHolder.binding.increase.setOnClickListener {
            item.quantity = item.quantity + 1
            viewHolder.binding.quantity.text = "${item.quantity}"
        }

        viewHolder.binding.decrease.setOnClickListener {
            if (item.quantity > 0){
                item.quantity = item.quantity - 1
                viewHolder.binding.quantity.text = "${item.quantity}"
            }
        }

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Medication>?) {
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

    class ViewHolder(val binding: CartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}