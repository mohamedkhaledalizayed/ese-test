package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.providerdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.PhoneItemBinding

class PhonesAdapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {

    private val items: MutableList<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PhoneItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.phone_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.binding.tvPhone.text = item
        viewHolder.binding.tvPhone.setOnClickListener { onItemClickListener?.setOnItemClickListener(item) }
//        if (position == itemCount - 1)
//            viewHolder.binding.view.visibility = View.GONE
    }

    override fun getItemCount() = items.size

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
    }

    class ViewHolder(val binding: PhoneItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}