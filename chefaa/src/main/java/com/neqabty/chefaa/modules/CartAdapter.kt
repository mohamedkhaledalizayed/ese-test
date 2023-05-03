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

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.status.visibility = GONE
        viewHolder.binding.medicationTitle.text = cart.productList[position].productEntity?.titleAr
        viewHolder.binding.quantity.text = "${cart.productList[position].quantity}"
        viewHolder.binding.medicationPrice.text =
            "${cart.productList[position].productEntity?.price?.times(cart.productList[position].quantity)} جنيه"

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
            } جنيه"
            onItemClickListener?.setOnItemClickListener()
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
                } جنيه"
            } else {
                //remove this item
                cart.productList.removeAt(position)
                submitList()
                notifyItemRangeChanged(0,0)
            }
            onItemClickListener?.setOnItemClickListener()
        }

    }

    override fun getItemCount() = cart.productList.size

    fun submitList() {
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener()
    }

    class ViewHolder(val binding: CartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}