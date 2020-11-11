package com.neqabty.presentation.ui.medicalRenewUpdate

import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.entities.MedicalRenewalUI

data class MedicalRenewUpdateViewState(
        var isLoading: Boolean = true,
        var medicalRenewalUI: MedicalRenewalUI? = null
)
