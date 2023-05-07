package com.neqabty.healthcare.core.packages

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.LayoutPackageItemBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages.PackagesEntity
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

        viewHolder.binding.cvPackage.setBackgroundResource(
            when (item.extension) {
                "AMA" -> R.drawable.ama_bg
                "PRZ" -> R.drawable.prz_bg
                "SLV" -> R.drawable.slv_bg
                "PLT" -> R.drawable.plt_bg
                "GLD" -> R.drawable.gold_bg
                else -> R.drawable.prz_bg
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