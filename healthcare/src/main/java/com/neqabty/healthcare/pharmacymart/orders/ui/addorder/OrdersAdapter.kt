package com.neqabty.healthcare.pharmacymart.orders.ui.addorder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.data.Constants.pharmacyMartCart
import com.neqabty.healthcare.databinding.PrescriptionLayoutItemBinding



class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PrescriptionLayoutItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.prescription_layout_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.delete.visibility = View.GONE
        viewHolder.binding.image.setImageURI(pharmacyMartCart.pharmacyMartImageList[position])

    }

    override fun getItemCount() = pharmacyMartCart.pharmacyMartImageList.size

    fun submitList() {
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnDeleteClickListener(position: Int)
    }

    class ViewHolder(val binding: PrescriptionLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}