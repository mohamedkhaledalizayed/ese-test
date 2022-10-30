package com.neqabty.chefaa.modules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.databinding.CartLayoutItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null


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
        if (position == itemCount - 1) {
            viewHolder.binding.view.visibility = View.GONE
        } else {
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        viewHolder.binding.status.visibility = GONE
        viewHolder.binding.medicationTitle.text = cart.productList[position].productEntity?.titleEn
        viewHolder.binding.quantity.text = "${cart.productList[position].quantity}"
        viewHolder.binding.medicationPrice.text =
            "${cart.productList[position].productEntity?.price?.times(cart.productList[position].quantity)}"

        Picasso.get()
            .load(cart.productList[position].productEntity?.image)
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
            cart.productList[position] =
                cart.productList[position].copy(quantity = cart.productList[position].quantity + 1)
            viewHolder.binding.quantity.text = "${cart.productList[position].quantity}"
            viewHolder.binding.medicationPrice.text = "${
                cart.productList[position].productEntity?.price?.times(
                    cart.productList[position].quantity
                )
            }"
        }

        //decrease
        viewHolder.binding.decrease.setOnClickListener {
            if (cart.productList[position].quantity > 1) {
                cart.productList[position] =
                    cart.productList[position].copy(quantity = cart.productList[position].quantity - 1)
                viewHolder.binding.quantity.text = "${cart.productList[position].quantity}"
                viewHolder.binding.medicationPrice.text = "${
                    cart.productList[position].productEntity?.price?.times(
                        cart.productList[position].quantity
                    )
                }"
            } else {
                //remove this item
                cart.productList.removeAt(position)
                submitList()
            }
        }

    }

    override fun getItemCount() = cart.productList.size

    fun submitList() {
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: CartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}