package com.neqabty.presentation.ui.trackShipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationItemBinding
import com.neqabty.databinding.ShipmentItemBinding
import com.neqabty.presentation.entities.TrackShipmentUI
import com.neqabty.ui.presentation.common.DataBoundListAdapter
import com.neqabty.ui.presentation.common.DataBoundViewHolder

class ShipmentsAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val callback: ((TrackShipmentUI) -> Unit)?
) : DataBoundListAdapter<TrackShipmentUI, ShipmentItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<TrackShipmentUI>() {
            override fun areItemsTheSame(oldItem: TrackShipmentUI, newItem: TrackShipmentUI): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: TrackShipmentUI, newItem: TrackShipmentUI): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): ShipmentItemBinding {
        val binding = DataBindingUtil
                .inflate<ShipmentItemBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.shipment_item,
                        parent,
                        false,
                        dataBindingComponent
                )
        binding.root.setOnClickListener {
            binding.trackShipmentUI?.let {
                callback?.invoke(it)
            }
        }
//        binding.ivTrackShipment.layoutParams.width = DisplayMetrics.width / 6
//        binding.ivTrackShipment.layoutParams.height = DisplayMetrics.width / 6
        return binding
    }

    override fun bind(binding: ShipmentItemBinding, item: TrackShipmentUI) {
        binding.trackShipmentUI = item
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ShipmentItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        if(position %2 == 0)
            holder.binding.llRoot.setBackgroundResource(R.drawable.provider_rounded_green_bg)
        else
            holder.binding.llRoot.setBackgroundResource(R.drawable.provider_rounded_blue_bg)
    }
}
