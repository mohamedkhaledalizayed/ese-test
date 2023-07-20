package com.neqabty.healthcare.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ServiceItemLayoutBinding
import com.neqabty.healthcare.payment.data.model.inquiryresponse.ServiceData


class ServicesAdapter : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private val mList = ArrayList<ServiceData>()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ServiceItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.service_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = mList[position]
        viewHolder.binding.serviceName.text = "${item.key_label}"
        viewHolder.binding.servicePrice.text = "${item.key_value} جنيه"

    }

    override fun getItemCount() = mList.size

    fun submitList(newItems: List<ServiceData>) {
        clear()
        newItems.let {
            mList.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ServiceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}