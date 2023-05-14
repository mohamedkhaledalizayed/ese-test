package com.neqabty.healthcare.chefaa.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.databinding.PhotoLayoutItemBinding


class PhotosAdapter (private val context: Context) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PhotoLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.photo_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.imageView.setImageURI(cart.imageList[position].imageUri)
        viewHolder.binding.deleteImage.visibility = View.VISIBLE


        viewHolder.binding.deleteImage.setOnClickListener { onItemClickListener?.setOnItemClickListener(position) }
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

    class ViewHolder(val binding: PhotoLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}