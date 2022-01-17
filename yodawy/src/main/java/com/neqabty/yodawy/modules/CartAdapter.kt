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
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.addOrIncrement
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.removeOrDecrement
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
        val item = items.map { it.first }[position]
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        viewHolder.binding.status.text = if(items[position].first.isLimitedAvailability) "Low Stock" else ""
        viewHolder.binding.medicationTitle.text = items[position].first.name
        viewHolder.binding.quantity.text = "${items[position].second}"
        viewHolder.binding.medicationPrice.text = "${items[position].first.regularPrice}"

        Picasso.get()
            .load(items[position].first.image).placeholder(R.drawable.drug_placeholder)
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
            val index = cartItems.addOrIncrement(item)
            items[position] = items[position].copy(second = items[index].second + 1)
            viewHolder.binding.quantity.text = "${items[index].second}"
            viewHolder.binding.medicationPrice.text = "${items[index].first.regularPrice}"
            onItemClickListener?.updateTotal()
        }

        //decrease
        viewHolder.binding.decrease.setOnClickListener {

            if (items[position].second > 1){
                cartItems.removeOrDecrement(item)
                items[position] = items[position].copy(second = items[position].second - 1)
                viewHolder.binding.quantity.text = "${items[position].second}"
                viewHolder.binding.medicationPrice.text = "${items[position].first.regularPrice}"
            }else{
                //remove this item
                cartItems.removeAt(position)
                submitList(cartItems)
                onItemClickListener?.notifyUi()
            }
            onItemClickListener?.updateTotal()
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
            fun updateTotal()
    }

    class ViewHolder(val binding: CartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}