package com.neqabty.chefaa.modules.orders.presentation.placeprescriptionscreen

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
import com.neqabty.chefaa.databinding.CheckoutCartLayoutItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class CheckoutCartAdapter : RecyclerView.Adapter<CheckoutCartAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CheckoutCartLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.checkout_cart_layout_item, parent, false)

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
        viewHolder.binding.medicationTitle.text = cart.productList[position].productEntity?.titleAr
        viewHolder.binding.medicationQuantity.text = "العدد : ${cart.productList[position].quantity}"
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
    }

    override fun getItemCount() = cart.productList.size

    fun submitList() {
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: CheckoutCartLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}