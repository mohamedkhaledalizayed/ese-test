package com.neqabty.healthcare.modules.search.presentation.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.PackageItemLayoutBinding
import com.neqabty.healthcare.modules.search.data.model.packages.PackageModel
import com.neqabty.healthcare.modules.search.presentation.model.search.PackageInfo
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class PackagesAdapter: RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {

    private val items: MutableList<PackageModel> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PackageItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.package_item_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        viewHolder.binding.packageName.text = item.name ?: ""
        viewHolder.binding.insuranceDetails.text = item.insuranceAmount ?: ""
        viewHolder.binding.requiredDataDetails.text = item.neddedInfo ?: ""
        viewHolder.binding.targetPeopleDetails.text = item.targetGroups ?: ""
        viewHolder.binding.infoDetails.text = item.description ?: ""
        viewHolder.binding.moreDetails.setOnClickListener {
            if (viewHolder.binding.packageDescription.isVisible){
                viewHolder.binding.packageDescription.visibility = View.GONE
                Picasso.get().load(R.drawable.ic_baseline_keyboard_arrow_down_24).placeholder(R.drawable.ic_baseline_keyboard_arrow_down_24).into(viewHolder.binding.moreIcon)
            }else{
                viewHolder.binding.packageDescription.visibility = View.VISIBLE
                Picasso.get().load(R.drawable.ic_baseline_keyboard_arrow_up_24).placeholder(R.drawable.ic_baseline_keyboard_arrow_up_24).into(viewHolder.binding.moreIcon)
            }
        }

        viewHolder.binding.btnSelect.setOnClickListener {
            onItemClickListener?.setOnRegisterClickListener("")
        }

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<PackageModel>?) {
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
            fun setOnRegisterClickListener(item: String)
    }

    class ViewHolder(val binding: PackageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}