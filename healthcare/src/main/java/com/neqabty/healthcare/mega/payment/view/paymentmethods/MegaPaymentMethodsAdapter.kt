package com.neqabty.healthcare.mega.payment.view.paymentmethods

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.PaymentMethodItemBinding
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.GatewaysData
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import java.util.*


open class MegaPaymentMethodsAdapter : RecyclerView.Adapter<MegaPaymentMethodsAdapter.ViewHolder>() {

    private val items: MutableList<GatewaysData> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PaymentMethodItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.payment_method_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    private var selectedPosition = 0
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        viewHolder.binding.tvName.text = item.display_name
        if (selectedPosition == position){
            viewHolder.binding.ivSelected.setImageResource(R.drawable.selected_icon)
        }else{
            viewHolder.binding.ivSelected.setImageResource(R.drawable.unselected_icon)
        }

        when (item.name) {
            "Opay Code" -> {
                viewHolder.binding.ivLogo.setImageResource(R.drawable.opay)
            }
            "Opay Card" -> {
                viewHolder.binding.ivLogo.setImageResource(R.drawable.visa)
            }
            "Fawry Code" -> {
                viewHolder.binding.ivLogo.setImageResource(R.drawable.fawry_icon)
            }
            else -> {

            }
        }
        viewHolder.binding.cvPayment.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
            selectedPosition = position
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<GatewaysData>) {
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
        fun setOnItemClickListener(item: GatewaysData)
    }

    class ViewHolder(val binding: PaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}