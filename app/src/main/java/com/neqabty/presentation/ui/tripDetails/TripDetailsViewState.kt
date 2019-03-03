package com.neqabty.presentation.ui.tripDetails

import com.neqabty.presentation.entities.TripUI

data class TripDetailsViewState(
        var isLoading: Boolean = true,
        var trip: TripUI? = null
)
