package com.neqabty.healthcare.commen.invoices.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.databinding.InvoiceItemLayoutBinding
import com.neqabty.healthcare.databinding.OfferItemBinding
import com.neqabty.healthcare.sustainablehealth.offers.domain.entity.offers.OffersEntity
import kotlin.collections.ArrayList


class InvoicesAdapter: RecyclerView.Adapter<InvoicesAdapter.ViewHolder>() {

    private val items: MutableList<InvoicesEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: InvoiceItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.invoice_item_layout, parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        viewHolder.binding.text.text = item.toString()
        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item.gatewayReferenceId)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<InvoicesEntity>?) {
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
    }

    class ViewHolder(val binding: InvoiceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}