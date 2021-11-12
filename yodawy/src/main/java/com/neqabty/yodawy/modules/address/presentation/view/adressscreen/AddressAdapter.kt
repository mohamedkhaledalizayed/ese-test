package com.neqabty.yodawy.modules.address.presentation.view.adressscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.AddressLayoutItemBinding
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity


class AddressAdapter: RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private val items: MutableList<AddressEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: AddressLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.address_layout_item, parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.binding.mainAddress.text = item.addressName
        viewHolder.binding.addressDetails.text = item.address
        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(position)
        }

        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<AddressEntity>?) {
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

    interface OnItemClickListener { fun setOnItemClickListener(itemId: Int) }

    class ViewHolder(val binding: AddressLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}