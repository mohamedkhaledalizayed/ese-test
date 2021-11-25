package com.neqabty.yodawy.modules.orders.presentation.view.orderdetailscreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.PrescriptionLayoutItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class PrescriptionsAdapter (private val context: Context) : RecyclerView.Adapter<PrescriptionsAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PrescriptionLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.prescription_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Picasso.get()
            .load(items[position].replace("\\", "/"))
            .into(viewHolder.binding.imageView, object : Callback {
                override fun onSuccess() {
                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    viewHolder.binding.imageProgress.hide()
                }
            })


        viewHolder.binding.imageView.setOnClickListener { onItemClickListener?.setOnItemClickListener(position) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<String>?) {
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

    class ViewHolder(val binding: PrescriptionLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}