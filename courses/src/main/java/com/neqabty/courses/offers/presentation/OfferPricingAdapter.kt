package com.neqabty.courses.offers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.PricingItemLayoutBinding
import com.neqabty.courses.offers.domain.entity.PricingEntity

class OfferPricingAdapter constructor(private var pricing : List<PricingEntity> = listOf()):
    RecyclerView.Adapter<OfferPricingAdapter.ViewHolder>() {
    class ViewHolder(private val binding: PricingItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(price: PricingEntity){
            binding.priceTv.text = price.price
            binding.studentTypeTv.text = price.studentCategoryEntity.name
        }
    }
    fun submitList(list:List<PricingEntity>){
        this.pricing = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PricingItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(price = pricing[position])
    }

    override fun getItemCount() = pricing.size
}