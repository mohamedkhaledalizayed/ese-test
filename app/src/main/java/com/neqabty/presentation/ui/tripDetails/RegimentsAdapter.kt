package com.neqabty.presentation.ui.tripDetails

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripRegimentItemBinding
import com.neqabty.presentation.entities.TripUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class RegimentsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((TripUI.TripRegiment) -> Unit)?
) : DataBoundListAdapter<TripUI.TripRegiment, TripRegimentItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<TripUI.TripRegiment>() {
        override fun areItemsTheSame(oldItem: TripUI.TripRegiment, newItem: TripUI.TripRegiment): Boolean {
            return oldItem.regimentId == newItem.regimentId
        }

        override fun areContentsTheSame(oldItem: TripUI.TripRegiment, newItem: TripUI.TripRegiment): Boolean {
            return oldItem.regimentId == newItem.regimentId
        }
    }
) {

    override fun createBinding(parent: ViewGroup): TripRegimentItemBinding {
        val binding = DataBindingUtil
            .inflate<TripRegimentItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.trip_regiment_item,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.regiment?.let {
                callback?.invoke(it)
            }
        }
//        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, DisplayMetrics.width /4)
//        binding.clLogo.layoutParams = layoutParams
        return binding
    }

    override fun bind(binding: TripRegimentItemBinding, item: TripUI.TripRegiment) {
        binding.regiment = item
    }
}
