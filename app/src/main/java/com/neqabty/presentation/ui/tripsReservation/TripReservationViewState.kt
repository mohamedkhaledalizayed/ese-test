package com.neqabty.presentation.ui.tripsReservation

import com.neqabty.presentation.entities.MemberUI

data class TripReservationViewState(
    var isLoading: Boolean = true,
    var member: MemberUI? = null
)
