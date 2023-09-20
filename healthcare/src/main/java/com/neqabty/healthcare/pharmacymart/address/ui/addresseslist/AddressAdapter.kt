package com.neqabty.healthcare.pharmacymart.address.ui.addresseslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.databinding.AddressLayoutItemBinding
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressEntity


class AddressAdapter: RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private val items: MutableList<PharmacyMartAddressEntity> = ArrayList()
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
        viewHolder.binding.addressDetails.text = "شارع ${item.streetName}, مبنى رقم ${item.buildingNo}, رقم الطابق ${item.floorNo}, شقة رقم ${item.apartmentNo}"
        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<PharmacyMartAddressEntity>?) {
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

    interface OnItemClickListener { fun setOnItemClickListener(addressItem: PharmacyMartAddressEntity) }

    class ViewHolder(val binding: AddressLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}