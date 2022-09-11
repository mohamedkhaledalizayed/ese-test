package com.neqabty.presentation.ui.syndicateServices

import com.neqabty.presentation.entities.*

data class SyndicateServicesViewState(
    var isLoading: Boolean = true,
    var serviceTypes: List<ServiceTypeUI.ServiceType>? = null,
    var services: List<ServiceTypeUI.Service>? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null
)
