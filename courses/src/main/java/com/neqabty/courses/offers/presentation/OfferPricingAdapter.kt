package com.neqabty.courses.offers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.PricingItemLayoutBinding
import com.neqabty.courses.offers.domain.entity.PricingEntity

class OfferPricingAdapter constructor(
    private var pricing: List<PricingEntity> = listOf(),
    private val onBtnClicked: (pricing: Int) -> Unit
) :
    RecyclerView.Adapter<OfferPricingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: PricingItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(price: PricingEntity,index:Int) {
            binding.priceTv.text = price.price
            binding.studentTypeTv.text = price.studentCategoryEntity.name
            binding.bookBtn.setOnClickListener {
                onBtnClicked(index)
            }
        }
    }

    fun submitList(list: List<PricingEntity>) {
        this.pricing = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PricingItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(price = pricing[position],position)
    }

    override fun getItemCount() = pricing.size
}