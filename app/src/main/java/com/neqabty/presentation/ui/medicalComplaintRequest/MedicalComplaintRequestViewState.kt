package com.neqabty.presentation.ui.medicalComplaintRequest

import com.neqabty.presentation.entities.*

data class MedicalComplaintRequestViewState(
    var isLoading: Boolean = true,
    var medicalComplaintsRequestUI: MedicalComplaintUI? = null
)
