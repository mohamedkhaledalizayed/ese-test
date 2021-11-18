package com.neqabty.yodawy.modules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.databinding.CartLayoutItemBinding
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val items: MutableList<Pair<ProductEntity,Int>> = ArrayList()
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

        viewHolder.binding.status.text = if(item.first.isLimitedAvailability) "Low Stock" else ""
        viewHolder.binding.medicationTitle.text = item.first.name
        viewHolder.binding.quantity.text = "${item.first.quantity}"
        viewHolder.binding.medicationPrice.text = "${item.first.regularPrice * item.first.quantity}"

        Picasso.get()
            .load(item.first.image)
            .into(viewHolder.binding.medicationImage, object : Callback {
                override fun onSuccess() {
                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    viewHolder.binding.imageProgress.hide()
                }
            })

        //increase
        viewHolder.binding.increase.setOnClickListener {
            item.first.quantity = item.first.quantity + 1
            viewHolder.binding.quantity.text = "${item.first.quantity}"
            viewHolder.binding.medicationPrice.text = "${item.first.regularPrice * item.first.quantity}"
        }

        //decrease
        viewHolder.binding.decrease.setOnClickListener {
            if (item.first.quantity > 1){
                item.first.quantity = item.first.quantity - 1
                viewHolder.binding.quantity.text = "${item.first.quantity}"
                viewHolder.binding.medicationPrice.text = "${item.first.regularPrice * item.first.quantity}"
            }else{
                //remove this item
                cartItems.removeAt(position)
                submitList(cartItems)
                onItemClickListener?.notifyUi()
            }
        }

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Pair<ProductEntity,Int>>?) {
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
            fun notifyUi()
    }

    class ViewHolder(val binding: CartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}