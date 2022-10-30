package com.neqabty.shealth.sustainablehealth.suggestions.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.shealth.R
import com.neqabty.shealth.databinding.ImageItemBinding
import kotlin.collections.ArrayList


class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private val items: MutableList<ImageInfo> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ImageItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.image_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.itemImage.setImageURI(item.image)
        viewHolder.binding.imageName.text = item.name ?: ""
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }
        viewHolder.binding.itemLayout.setOnClickListener { onItemClickListener?.setOnItemClickListener(item) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<ImageInfo>?) {
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
            fun setOnItemClickListener(item: ImageInfo)
    }

    class ViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}