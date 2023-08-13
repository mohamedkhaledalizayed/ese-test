package com.neqabty.healthcare.mypackages.packages.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.LayoutPackageItemBinding
import com.neqabty.healthcare.mypackages.packages.domain.entity.PackageEntity
import com.neqabty.healthcare.mypackages.packages.domain.entity.SubscribedPackageEntity
import java.util.*


class MyPackagesAdapter : RecyclerView.Adapter<MyPackagesAdapter.ViewHolder>() {

    private val items: MutableList<SubscribedPackageEntity> = ArrayList()
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
        val item = items[position].packages
        viewHolder.binding.tvName.text = item.name
        viewHolder.binding.tvPrice.text = "${item.packagePrice?.toInt()}"
        viewHolder.binding.tvDescription.text = item.descriptionAr


        viewHolder.binding.cvPackage.setOnClickListener { onItemClickListener?.setOnItemClickListener(item) }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<SubscribedPackageEntity>) {
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
        fun setOnItemClickListener(item: PackageEntity)
    }

    class ViewHolder(val binding: LayoutPackageItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}