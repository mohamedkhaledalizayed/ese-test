package com.neqabty.yodawy.modules

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.PhotoLayoutItemBinding


class PhotosAdapter (private val context: Context) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val items: MutableList<Medication> = ArrayList()
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

    }

    override fun getItemCount() = 5

    fun submitList(newItems: List<Medication>?) {
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