package com.neqabty.healthcare.chefaa.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.databinding.CartItemBinding


class CartItemsAdapter : RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CartItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.cart_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.productName.text = "${cart.productList[position].productEntity?.titleAr}"
        viewHolder.binding.productPrice.text = "${cart.productList[position].productEntity?.price} جنيه"

    }

    override fun getItemCount() = cart.productList.size

    fun submitList() {
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener()
    }

    class ViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}