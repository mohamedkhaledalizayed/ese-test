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
import com.neqabty.yodawy.databinding.ProductLayoutItemBinding
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private val items: MutableList<Pair<ProductEntity,Int>> = ArrayList()
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

        viewHolder.binding.status.text = if(item.first.isLimitedAvailability) "Low Stock" else ""
        viewHolder.binding.medicationTitle.text = item.first.name
        viewHolder.binding.medicationQuantity.text = "العدد : ${item.first.quantity}"
        viewHolder.binding.medicationPrice.text = "${item.first.regularPrice} جنيه"

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
    }

    class ViewHolder(val binding: ProductLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}