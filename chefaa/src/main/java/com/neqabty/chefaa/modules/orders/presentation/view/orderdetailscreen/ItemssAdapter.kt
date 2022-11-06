package com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.databinding.ProductLayoutItemBinding
import com.neqabty.chefaa.modules.orders.domain.entities.ItemEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items: MutableList<ItemEntity> = ArrayList()
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

        viewHolder.binding.medicationTitle.text = item.productName
        viewHolder.binding.medicationQuantity.text = "العدد : ${item.quantity}"
        Picasso.get()
            .load(item.productImage)
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

    fun submitList(newItems: List<ItemEntity>) {
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