package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.MedicationLayoutItemBinding
import com.neqabty.yodawy.modules.Medication


class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val items: MutableList<Medication> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: MedicationLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.medication_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }

        if (item.status == 1){
            viewHolder.binding.addItem.visibility = View.VISIBLE
            viewHolder.binding.viewDetails.visibility = View.GONE
            viewHolder.binding.medicationStatus.setImageResource(R.drawable.check_mark)
            viewHolder.binding.status.visibility = View.GONE
            viewHolder.binding.deliveryTime.visibility = View.VISIBLE
        }else{
            viewHolder.binding.status.visibility = View.VISIBLE
            if (item.status == 2){
                viewHolder.binding.medicationStatus.visibility = View.VISIBLE
                viewHolder.binding.medicationStatus.setImageResource(R.drawable.exclamation)
                viewHolder.binding.status.text = "Low Stock"
                viewHolder.binding.status.setBackgroundResource(R.color.red)
                viewHolder.binding.deliveryTime.visibility = View.VISIBLE
                viewHolder.binding.deliveryTime.text = "May not be available"
            }else{
                viewHolder.binding.status.setBackgroundResource(R.color.address_btn_bg)
                viewHolder.binding.medicationStatus.visibility = View.INVISIBLE
                viewHolder.binding.deliveryTime.visibility = View.GONE
                viewHolder.binding.status.text = "Out of Stock"
            }
            viewHolder.binding.viewDetails.visibility = View.VISIBLE
            viewHolder.binding.addItem.visibility = View.GONE
        }

        viewHolder.binding.addItem.setOnClickListener {
            viewHolder.binding.increaseDecrease.visibility = View.VISIBLE
            viewHolder.binding.addItem.visibility = View.GONE
        }

        viewHolder.binding.viewDetails.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item.status)
        }

        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item.status)
        }
    }

    override fun getItemCount() = items.size

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

    class ViewHolder(val binding: MedicationLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}