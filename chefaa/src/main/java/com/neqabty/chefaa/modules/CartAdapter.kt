package com.neqabty.chefaa.modules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants.cartItems
import com.neqabty.chefaa.databinding.CartLayoutItemBinding
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
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
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        viewHolder.binding.status.visibility = GONE
        viewHolder.binding.medicationTitle.text = items[position].first.titleEn
        viewHolder.binding.quantity.text = "${items[position].second}"
        viewHolder.binding.medicationPrice.text = "${items[position].first.price * items[position].second}"

        Picasso.get()
            .load(items[position].first.image)
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
            items[position] = items[position].copy(second = items[position].second + 1)
            viewHolder.binding.quantity.text = "${items[position].second}"
            viewHolder.binding.medicationPrice.text = "${items[position].first.price * items[position].second}"
        }

        //decrease
        viewHolder.binding.decrease.setOnClickListener {
            if (items[position].second > 1){
                items[position] = items[position].copy(second = items[position].second - 1)
                viewHolder.binding.quantity.text = "${items[position].second}"
                viewHolder.binding.medicationPrice.text = "${items[position].first.price * items[position].second}"
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