package com.neqabty.presentation.ui.medicalRenewUpdate

import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MedicalRenewalUpdateUI

data class MedicalRenewUpdateViewState(
        var isLoading: Boolean = true,
        var medicalRenewalUI: MedicalRenewalUI? = null,
        var medicalRenewalUpdateUI: MedicalRenewalUpdateUI? = null
)
