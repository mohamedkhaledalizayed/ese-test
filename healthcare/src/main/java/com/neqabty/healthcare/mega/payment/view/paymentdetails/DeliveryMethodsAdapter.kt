package com.neqabty.healthcare.mega.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.DeliveryMethodItemBinding
import com.neqabty.healthcare.databinding.PaymentMethodItemBinding
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import java.util.*


open class DeliveryMethodsAdapter : RecyclerView.Adapter<DeliveryMethodsAdapter.ViewHolder>() {

    private val items: MutableList<PaymentMethodEntity> = ArrayList()
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

    private var selectedPosition = 0
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.tvName.text = item.displayName
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

    fun submitList(newItems: List<PaymentMethodEntity>) {
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
        fun setOnItemClickListener(item: PaymentMethodEntity)
    }

    class ViewHolder(val binding: DeliveryMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}