package com.neqabty.courses.offers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.DatesItemLayoutBinding
import com.neqabty.courses.offers.domain.entity.AppointmentEntity

class OfferDatesAdapter constructor(private var dates : List<AppointmentEntity> = listOf()):RecyclerView.Adapter<OfferDatesAdapter.ViewHolder>() {
    class ViewHolder(private val binding:DatesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(date:AppointmentEntity){
            binding.dayTitleTv.text = date.day.toString()
            binding.fromTimeTv.text = date.timeFrom
            binding.toTimeTv.text = date.timeTo
        }
    }
    fun submitList(list:List<AppointmentEntity>){
        this.dates = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DatesItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(date = dates[position])
    }

    override fun getItemCount() = dates.size
}