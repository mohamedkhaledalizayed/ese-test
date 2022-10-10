package com.neqabty.presentation.ui.syndicateServicesDetails

import com.neqabty.presentation.entities.*

data class SyndicateServicesDetailsViewState(
    var isLoading: Boolean = false,
    var serviceTypes: List<SyndicateServicesUI.ServiceType>? = null,
    var services: List<SyndicateServicesUI.Service>? = null,
    var renewalPayment: RenewalPaymentUI? = null
)
