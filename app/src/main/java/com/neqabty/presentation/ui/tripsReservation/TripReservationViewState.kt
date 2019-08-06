package com.neqabty.presentation.ui.tripsReservation

import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.TripUI

data class TripReservationViewState(
    var isLoading: Boolean = true,
    var member: MemberUI? = null
)
