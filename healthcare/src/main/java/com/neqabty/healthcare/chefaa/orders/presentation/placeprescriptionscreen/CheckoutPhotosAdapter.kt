package com.neqabty.healthcare.chefaa.orders.presentation.placeprescriptionscreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.databinding.CheckoutPhotoLayoutItemBinding


class CheckoutPhotosAdapter (private val context: Context) : RecyclerView.Adapter<CheckoutPhotosAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CheckoutPhotoLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.checkout_photo_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.imageView.setImageURI(cart.imageList[position].imageUri)
    }

    override fun getItemCount() = cart.imageList.size

    fun submitList() {
        notifyDataSetChanged()
    }

    fun clear() {
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(itemId: Int)
    }

    class ViewHolder(val binding: CheckoutPhotoLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}