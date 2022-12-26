package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen

import android.annotation.SuppressLint
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.CardItemBinding
import com.neqabty.healthcare.databinding.PackageItemLayoutBinding
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.DetailEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class PackagesAdapter: RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {

    private val items: MutableList<PackagesEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CardItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.card_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        viewHolder.binding.packageName.text = item.name
        viewHolder.binding.info.text = item.description
//        viewHolder.binding.infoDetails.text = item.description
        viewHolder.binding.packagePrice.text = "${item.price.toInt()} جنيه - للفرد"
            .replace("1", "١").replace("2", "٢")
        .replace("3", "٣").replace("4", "٤")
        .replace("5", "٥").replace("6", "٦")
        .replace("7", "٧").replace("8", "٨")
        .replace("9", "٩").replace("0", "٠")

        when (item.extension) {
            "AMA" -> {
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_57)
            }
            "PRZ" -> {
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_bro)
            }
            "SLV" -> {
                viewHolder.binding.packageImage.setImageResource(R.drawable.silver_image)
            }
            "PLT" -> {
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_plat)
            }
            "GLD" -> {
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_58)
            }
        }
        var details = ""
        for (item: DetailEntity in item.details){
                details = "$details <h4> ${item.title}.</h4> \n ${item.description.replace("\r", " ").replace("\n", "")}. \n"
        }

        viewHolder.binding.selectBtn.setOnClickListener {
            onItemClickListener?.setOnRegisterClickListener(item)
        }
        viewHolder.binding.moreDetails.setOnClickListener {
            onItemClickListener?.setOnMoreClickListener(item.name, details, item.serviceCode)
        }

    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<PackagesEntity>?) {
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
            fun setOnRegisterClickListener(item: PackagesEntity)
            fun setOnMoreClickListener(title: String, content: String, code: String)
    }

    class ViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}