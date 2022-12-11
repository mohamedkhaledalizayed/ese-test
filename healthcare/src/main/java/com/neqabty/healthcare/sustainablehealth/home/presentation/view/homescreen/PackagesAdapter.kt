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
        viewHolder.binding.infoDetails.text = item.description
        viewHolder.binding.packagePrice.text = "${item.price.toInt()} جنية"
            .replace("1", "١").replace("2", "٢")
        .replace("3", "٣").replace("4", "٤")
        .replace("5", "٥").replace("6", "٦")
        .replace("7", "٧").replace("8", "٨")
        .replace("9", "٩").replace("0", "٠")

        when (item.serviceCode) {
            "P8152" -> {
                viewHolder.binding.packageColor.setBackgroundResource(R.drawable.save_package)
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_57)
            }
            "P7356" -> {
                viewHolder.binding.packageColor.setBackgroundResource(R.drawable.bro_package)
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_bro)
            }
            "P5906" -> {
                viewHolder.binding.packageColor.setBackgroundResource(R.drawable.silver_package)
                viewHolder.binding.packageImage.setImageResource(R.drawable.silver_image)
            }
            "P5421" -> {
                viewHolder.binding.packageColor.setBackgroundResource(R.drawable.plat_package)
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_plat)
            }
            "P5280" -> {
                viewHolder.binding.packageColor.setBackgroundResource(R.drawable.gold_package)
                viewHolder.binding.packageImage.setImageResource(R.drawable.image_58)
            }
        }
        var details = ""
        for (item: DetailEntity in item.details){
                details = "$details ${item.title}: ${item.description.replace("\r", " ").replace("\n", "")}. \n"
        }

        viewHolder.binding.detailsValue.text = details

        viewHolder.binding.cardView.setOnClickListener {
            onItemClickListener?.setOnRegisterClickListener(item)
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
    }

    class ViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}