package com.neqabty.healthcare.pharmacymart.orders.ui.uploadprescription

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.databinding.PrescriptionLayoutItemBinding
import java.util.*


class PrescriptionsAdapter : RecyclerView.Adapter<PrescriptionsAdapter.ViewHolder>() {

    private val items: MutableList<Uri?> = ArrayList()
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

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.delete.setOnClickListener { onItemClickListener?.setOnDeleteClickListener(position) }
    }

    override fun getItemCount() = Constants.pharmacyMartCart.pharmacyMartImageList.size

    fun submitList(newItems: List<Uri?>?) {
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
        fun setOnDeleteClickListener(position: Int)
    }

    class ViewHolder(val binding: PrescriptionLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}