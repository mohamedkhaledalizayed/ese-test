package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.databinding.MedicationLayoutItemBinding
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class SearchAdapter(val invalidateMenuCallback: () -> Unit) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

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
            .load(item.image)
            .into(viewHolder.binding.medicationImage, object : Callback {
                override fun onSuccess() {
                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    viewHolder.binding.imageProgress.hide()
                }
            })

        when {
            item.outOfStock -> {
                viewHolder.binding.viewDetails.visibility = View.VISIBLE
                viewHolder.binding.addItem.visibility = View.GONE
                viewHolder.binding.status.setBackgroundResource(R.color.address_btn_bg)
                viewHolder.binding.medicationStatus.visibility = View.INVISIBLE
                viewHolder.binding.deliveryTime.visibility = View.GONE
                viewHolder.binding.status.text = "Out of Stock"
            }
            item.isLimitedAvailability -> {
                viewHolder.binding.viewDetails.visibility = View.VISIBLE
                viewHolder.binding.addItem.visibility = View.GONE
                viewHolder.binding.medicationStatus.visibility = View.VISIBLE
                viewHolder.binding.medicationStatus.setImageResource(R.drawable.exclamation)
                viewHolder.binding.status.text = "Low Stock"
                viewHolder.binding.status.setBackgroundResource(R.color.red)
                viewHolder.binding.deliveryTime.visibility = View.VISIBLE
                viewHolder.binding.deliveryTime.text = "May not be available"
            }
            else -> {
                viewHolder.binding.addItem.visibility = View.VISIBLE
                viewHolder.binding.viewDetails.visibility = View.GONE
                viewHolder.binding.medicationStatus.setImageResource(R.drawable.check_mark)
                viewHolder.binding.status.visibility = View.GONE
                viewHolder.binding.deliveryTime.visibility = View.VISIBLE
            }
        }

        viewHolder.binding.medicationTitle.text = item.name
        viewHolder.binding.medicationPrice.text = "EGP ${item.salePrice.toString()}"

        viewHolder.binding.addItem.setOnClickListener {
            viewHolder.binding.increaseDecrease.visibility = View.VISIBLE
            viewHolder.binding.addItem.visibility = View.GONE
            //TODO confirm to clear cart
            cartItems.addOrIncrement(item)
            invalidateMenuCallback.invoke()
        }

        viewHolder.binding.viewDetails.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        //show saved items
        cartItems.mapIndexed{ ind, product ->
            if (product.first.id == item.id){
                viewHolder.binding.increaseDecrease.visibility = View.VISIBLE
                viewHolder.binding.addItem.visibility = View.GONE
                viewHolder.binding.quantity.text = "${cartItems[ind].first.quantity}"
            }
        }

        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        //increase
        viewHolder.binding.increase.setOnClickListener {
            cartItems.mapIndexed{ ind, product ->
                if (product.first.id == item.id){
                    cartItems[ind].first.quantity = cartItems[ind].first.quantity + 1
                    viewHolder.binding.quantity.text = "${cartItems[ind].first.quantity}"
                }
            }
            invalidateMenuCallback.invoke()
        }

//        //decrease
        viewHolder.binding.decrease.setOnClickListener {

            cartItems.mapIndexed{ ind, product ->
                if (product.first.id == item.id){
                    if (cartItems[ind].first.quantity > 1){
                        cartItems[ind].first.quantity = cartItems[ind].first.quantity - 1
                        viewHolder.binding.quantity.text = "${cartItems[ind].first.quantity}"
                    }else{
                        //remove this item
                        cartItems.removeAt(ind)
                        viewHolder.binding.increaseDecrease.visibility = View.GONE
                        if (item.active){
                            viewHolder.binding.addItem.visibility = View.VISIBLE
                        }else{
                            viewHolder.binding.viewDetails.visibility = View.VISIBLE
                        }
                    }
                }
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
    }

    class ViewHolder(val binding: MedicationLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}