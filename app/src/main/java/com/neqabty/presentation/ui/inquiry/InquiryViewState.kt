package com.neqabty.presentation.ui.inquiry

import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI

data class InquiryViewState(
    var isLoading: Boolean = true,
    var serviceTypes: List<ServiceTypeUI>? = null,
    var services: List<ServiceUI>? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null
)
