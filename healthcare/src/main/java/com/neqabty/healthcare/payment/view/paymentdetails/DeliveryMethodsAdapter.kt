package com.neqabty.healthcare.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.DeliveryMethodItemBinding
import com.neqabty.healthcare.payment.data.model.inquiryresponse.DeliveryMethod
import java.util.*


open class DeliveryMethodsAdapter : RecyclerView.Adapter<DeliveryMethodsAdapter.ViewHolder>() {

    private val items: MutableList<DeliveryMethod> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: DeliveryMethodItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.delivery_method_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    private var selectedPosition = -1
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.tvName.text = item.method
        viewHolder.binding.tvPrice.text = "تكلفة ال${item.method} ${item.price} جنيه"
        if (selectedPosition == position){
            viewHolder.binding.ivSelected.setImageResource(R.drawable.selected_icon)
        }else{
            viewHolder.binding.ivSelected.setImageResource(R.drawable.unselected_icon)
        }

        viewHolder.binding.cvPayment.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
            selectedPosition = position
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<DeliveryMethod>) {
        clear()
        newItems.let {
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
        fun setOnItemClickListener(item: DeliveryMethod)
    }

    class ViewHolder(val binding: DeliveryMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}