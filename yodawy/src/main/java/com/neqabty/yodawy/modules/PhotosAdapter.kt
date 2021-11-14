package com.neqabty.yodawy.modules

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.databinding.PhotoLayoutItemBinding


class PhotosAdapter (private val context: Context) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val items: MutableList<Uri> = ArrayList()
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
        if (position == 0){
            viewHolder.binding.imageView.setImageResource(R.drawable.camera)
            viewHolder.binding.deleteImage.visibility = View.GONE
            viewHolder.binding.root.setOnClickListener { onItemClickListener?.setOnItemClickListener(0) }
        }else{
            viewHolder.binding.imageView.setImageURI(items[position])
            viewHolder.binding.deleteImage.visibility = View.VISIBLE
        }

        viewHolder.binding.deleteImage.setOnClickListener { onItemClickListener?.setOnItemClickListener(position) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Uri>?) {
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

    class ViewHolder(val binding: PhotoLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}