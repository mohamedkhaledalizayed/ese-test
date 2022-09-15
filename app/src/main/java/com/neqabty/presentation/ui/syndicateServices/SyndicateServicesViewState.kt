package com.neqabty.presentation.ui.syndicateServices

import com.neqabty.presentation.entities.*

data class SyndicateServicesViewState(
    var isLoading: Boolean = true,
    var serviceTypes: List<SyndicateServicesUI.ServiceType>? = null,
    var services: List<SyndicateServicesUI.Service>? = null,
    var renewalPayment: RenewalPaymentUI? = null
)
