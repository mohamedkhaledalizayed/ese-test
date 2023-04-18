package com.neqabty.healthcare.core.packages

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.LayoutPackageItemBinding
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.DetailEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import java.util.*


class PackagesAdapter : RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {

    private val items: MutableList<PackagesEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: LayoutPackageItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.layout_package_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.binding.tvName.text = item.name
        viewHolder.binding.tvPrice.text = "${item.price.toInt()} جنيه - للفرد"
        viewHolder.binding.tvDescription.text = item.description

        val parentContext = viewHolder.binding.root.context
        viewHolder.binding.cvPackage.setCardBackgroundColor(
            when (item.extension) {
                "AMA" -> parentContext.getColor(R.color.package_safe)
                "PRZ" -> parentContext.getColor(R.color.package_bronze)
                "SLV" -> parentContext.getColor(R.color.package_silver)
                "PLT" -> parentContext.getColor(R.color.package_platinum)
                "GLD" -> parentContext.getColor(R.color.package_gold)
                else -> parentContext.getColor(R.color.white)
            }
        )

        viewHolder.binding.cvPackage.setOnClickListener { onItemClickListener?.setOnItemClickListener(item) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<PackagesEntity>) {
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
        fun setOnItemClickListener(item: PackagesEntity)
    }

    class ViewHolder(val binding: LayoutPackageItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}