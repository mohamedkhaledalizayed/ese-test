package com.neqabty.courses.reservations.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.ReservationItemLayoutBinding
import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity


class ReservationsAdapter(private var list: List<CourseReservationEntity> = listOf(), private val onClick:(reservation: CourseReservationEntity)->Unit) : RecyclerView.Adapter<ReservationsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ReservationItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(reservation: CourseReservationEntity){

            binding.titleTV.text = reservation.courseName
            binding.contactValue.text = reservation.contactPhone
            binding.root.setOnClickListener {
                onClick(reservation)
            }
        }
    }
    fun submitList(reservation: List<CourseReservationEntity>){
        this.list = reservation
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ReservationItemLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}