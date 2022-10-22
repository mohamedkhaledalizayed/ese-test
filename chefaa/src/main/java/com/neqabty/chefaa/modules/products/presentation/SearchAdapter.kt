package com.neqabty.chefaa.modules.products.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cartItems
//import com.neqabty.chefaa.core.data.Constants.cartItems
import com.neqabty.chefaa.core.utils.replaceText
import com.neqabty.chefaa.databinding.MedicationLayoutItemBinding
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
//import com.neqabty.chefaa.modules.products.domain.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class SearchAdapter(val invalidateMenuCallback: () -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val items: MutableList<ProductEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: MedicationLayoutItemBinding =
            DataBindingUtil.inflate(
                layoutInflater!!,
                R.layout.medication_layout_item,
                parent,
                false
            )

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        if (position == itemCount - 1) {
            viewHolder.binding.view.visibility = View.GONE
        } else {
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        Picasso.get()
            .load(item.image?.replaceText())
            .into(viewHolder.binding.medicationImage, object : Callback {
                override fun onSuccess() {
                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    viewHolder.binding.imageProgress.hide()
                }
            })
        viewHolder.binding.status.visibility = View.GONE
        viewHolder.binding.medicationTitle.text = item.titleAr
        viewHolder.binding.medicationPrice.text = "EGP ${item.price.toString()}"

        viewHolder.binding.addItem.setOnClickListener {
            if (Constants.imageList.isEmpty()) {
                viewHolder.binding.increaseDecrease.visibility = View.VISIBLE
                viewHolder.binding.addItem.visibility = View.GONE
                //TODO confirm to clear cart
                cartItems.addOrIncrement(item)
                invalidateMenuCallback.invoke()
            }
            onItemClickListener?.setOnAddItemClickListener()
        }

        viewHolder.binding.viewDetails.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        //show saved items
        cartItems.mapIndexed { ind, product ->
            if (product.first.id == item.id) {
                viewHolder.binding.increaseDecrease.visibility = View.VISIBLE
                viewHolder.binding.addItem.visibility = View.GONE
                viewHolder.binding.viewDetails.visibility = View.GONE
                viewHolder.binding.quantity.text = "${cartItems[ind].second}"
            }
        }

        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        //increase
        viewHolder.binding.increase.setOnClickListener {
            val index = cartItems.addOrIncrement(item)
            viewHolder.binding.quantity.text = "${cartItems[index].second}"
            invalidateMenuCallback.invoke()
        }

//        //decrease
        viewHolder.binding.decrease.setOnClickListener {
            val index = cartItems.removeOrDecrement(item)
            if (index == -1) {
                viewHolder.binding.increaseDecrease.visibility = View.GONE
                viewHolder.binding.addItem.visibility = View.VISIBLE
            } else {
                viewHolder.binding.quantity.text = "${cartItems[index].second}"
            }
            invalidateMenuCallback.invoke()
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<ProductEntity>?) {
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
        fun setOnItemClickListener(item: ProductEntity)
        fun setOnAddItemClickListener()
    }

    class ViewHolder(val binding: MedicationLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}