package com.neqabty.healthcare.commen.mypackages.subscriptiondetails.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.InsuranceItemLayoutBinding
import kotlin.collections.ArrayList


class InsuranceAdapter: RecyclerView.Adapter<InsuranceAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: InsuranceItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.insurance_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]

        when (position) {
            0 -> {
                viewHolder.binding.memberNumber.text = "الفرد الاول."
            }
            1 -> {
                viewHolder.binding.memberNumber.text = "الفرد الثانى."
            }
            2 -> {
                viewHolder.binding.memberNumber.text = "الفرد الثالث."
            }
            3 -> {
                viewHolder.binding.memberNumber.text = "الفرد الرابع."
            }
            4 -> {
                viewHolder.binding.memberNumber.text = "الفرد الخامس."
            }
            5 -> {
                viewHolder.binding.memberNumber.text = "الفرد السادس."
            }
        }
        viewHolder.binding.root.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<String>) {
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
            fun setOnItemClickListener(item: String)
    }

    class ViewHolder(val binding: InsuranceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}