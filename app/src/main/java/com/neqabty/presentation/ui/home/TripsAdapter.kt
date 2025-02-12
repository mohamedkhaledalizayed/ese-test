package com.neqabty.presentation.ui.home

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripsCardBinding
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class TripsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((TripUI) -> Unit)?
) : DataBoundListAdapter<TripUI, TripsCardBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<TripUI>() {
        override fun areItemsTheSame(oldItem: TripUI, newItem: TripUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TripUI, newItem: TripUI): Boolean {
            return oldItem.img == newItem.img
        }
    }
) {

    override fun createBinding(parent: ViewGroup): TripsCardBinding {
        val binding = DataBindingUtil
            .inflate<TripsCardBinding>(
                LayoutInflater.from(parent.context),
                R.layout.trips_card,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.trip?.let {
                callback?.invoke(it)
            }
        }
        binding.clHolder.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, DisplayMetrics.height * 20 / 100)
        return binding
    }

    override fun bind(binding: TripsCardBinding, item: TripUI) {
        binding.trip = item
    }
}
