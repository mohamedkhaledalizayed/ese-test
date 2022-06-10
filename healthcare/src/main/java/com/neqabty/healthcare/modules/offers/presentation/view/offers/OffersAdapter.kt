package com.neqabty.healthcare.modules.offers.presentation.view.offers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.OfferItemBinding
import kotlin.collections.ArrayList


class OffersAdapter: RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: OfferItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.offer_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.discountName.setOnClickListener { onItemClickListener?.setOnTakeClickListener("") }
        viewHolder.binding.useIt.setOnClickListener { onItemClickListener?.setOnCashBackClickListener("") }
    }

    override fun getItemCount() = 12

    fun submitList(newItems: MutableList<String>?) {
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
            fun setOnItemClickListener(item: String)
            fun setOnTakeClickListener(item: String)
            fun setOnCashBackClickListener(item: String)
    }

    class ViewHolder(val binding: OfferItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}