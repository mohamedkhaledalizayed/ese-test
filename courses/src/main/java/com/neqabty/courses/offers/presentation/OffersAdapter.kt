package com.neqabty.courses.offers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.OfferItemLayoutBinding
import com.neqabty.courses.offers.domain.entity.OfferEntity

class OffersAdapter(private var offersList:List<OfferEntity> = listOf(),private val onClick:(offer:OfferEntity)->Unit) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: OfferItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(offer:OfferEntity){
            binding.titleTV.text = offer.title
            binding.startDateValue.text = offer.startDate
            binding.endDateValue.text = offer.endDate
            binding.contactValue.text = offer.contact
            binding.peopleValue.text = offer.numOfTrainees.toString()
            binding.root.setOnClickListener {
                onClick(offer)
            }
        }
    }
    fun submitList(offers:List<OfferEntity>){
        this.offersList = offers
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = OfferItemLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(offersList[position])
    }

    override fun getItemCount() = offersList.size
}