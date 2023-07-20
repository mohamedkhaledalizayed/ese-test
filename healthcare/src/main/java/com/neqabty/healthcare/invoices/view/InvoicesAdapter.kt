package com.neqabty.healthcare.invoices.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.databinding.InvoiceItemLayoutBinding
import com.neqabty.healthcare.invoices.domain.entity.InvoicesEntity


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
        viewHolder.binding.paymentName.text = item.serviceName
        viewHolder.binding.paymentDate.text = AppUtils().dateFormat(item.createdAt)
        viewHolder.binding.total.text = "${item.totalAmount} جنيه"

        if (item.status == "SUCCESS"){
            viewHolder.binding.paymentStatus.text = "مكتملة"
            viewHolder.binding.paymentStatus.setBackgroundResource(R.drawable.order_status_accepted_bg)
        }else{
            viewHolder.binding.paymentStatus.text = "غير مكتملة"
            viewHolder.binding.paymentStatus.setBackgroundResource(R.drawable.order_status_canceled_bg)
        }
        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
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
        fun setOnItemClickListener(item: InvoicesEntity)
    }

    class ViewHolder(val binding: InvoiceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}