package com.neqabty.presentation.ui.trackShipment

import com.neqabty.presentation.entities.TrackShipmentUI

data class TrackShipmentViewState(
    var isLoading: Boolean = false,
    var trackShipmentList: List<TrackShipmentUI>? = null
)
