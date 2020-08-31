package com.neqabty.presentation.ui.trips

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripsItemBinding
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class TripsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((TripUI) -> Unit)?
) : DataBoundListAdapter<TripUI, TripsItemBinding>(
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

    override fun createBinding(parent: ViewGroup): TripsItemBinding {
        val binding = DataBindingUtil
            .inflate<TripsItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.trips_item,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.trip?.let {
                callback?.invoke(it)
            }
        }
        binding.ivLogo.layoutParams.height = DisplayMetrics.width / 6
        binding.ivLogo.requestLayout()

        return binding
    }

    override fun bind(binding: TripsItemBinding, item: TripUI) {
        binding.trip = item
    }
}
