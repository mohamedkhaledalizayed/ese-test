package com.neqabty.courses.offers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.DatesItemLayoutBinding
import com.neqabty.courses.offers.domain.entity.AppointmentEntity

class OfferDatesAdapter constructor(private var dates : List<AppointmentEntity> = listOf()):RecyclerView.Adapter<OfferDatesAdapter.ViewHolder>() {
    class ViewHolder(private val binding:DatesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(date:AppointmentEntity){
            binding.priceTv.text = "Saturday"
            binding.studentTypeTv.text = date.timeFrom
            binding.endTime.text = date.timeTo
        }
    }
    fun submitList(list:List<AppointmentEntity>){
        this.dates = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DatesItemLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(date = dates[position])
    }

    override fun getItemCount() = dates.size
}