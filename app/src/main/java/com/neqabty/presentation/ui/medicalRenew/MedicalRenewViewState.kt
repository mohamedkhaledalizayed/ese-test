package com.neqabty.presentation.ui.medicalRenew

import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MedicalRenewalUI

data class MedicalRenewViewState(
        var isLoading: Boolean = true,
        var medicalRenewalUI: MedicalRenewalUI? = null,
        var medicalRenewalPayment: MedicalRenewalPaymentUI? = null
)
