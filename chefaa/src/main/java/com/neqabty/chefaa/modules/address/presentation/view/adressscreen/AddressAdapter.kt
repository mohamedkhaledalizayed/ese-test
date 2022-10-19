package com.neqabty.chefaa.modules.address.presentation.view.adressscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.chefaa.R
import com.neqabty.chefaa.databinding.AddressLayoutItemBinding
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity


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
        viewHolder.binding.mainAddress.text = item.title
        viewHolder.binding.addressDetails.text = "شارع ${item.address}, مبنى رقم ${item.buildingNo}, رقم الطابق ${item.floorNo}, شقة رقم ${item.apartmentNo}"
        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }

        if (position == itemCount - 1){
            viewHolder.binding.view.visibility = View.GONE
        }else{
            viewHolder.binding.view.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<AddressEntity>?) {
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

    interface OnItemClickListener { fun setOnItemClickListener(addressItem: AddressEntity) }

    class ViewHolder(val binding: AddressLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}