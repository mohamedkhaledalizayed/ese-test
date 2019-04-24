package com.neqabty.presentation.ui.trips

import com.neqabty.presentation.entities.TripUI

data class TripsViewState(
    var isLoading: Boolean = true,
    var trips: List<TripUI>? = null
)
