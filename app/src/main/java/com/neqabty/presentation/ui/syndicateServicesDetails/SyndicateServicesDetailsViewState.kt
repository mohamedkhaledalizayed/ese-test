package com.neqabty.presentation.ui.syndicateServicesDetails

import com.neqabty.presentation.entities.*

data class SyndicateServicesDetailsViewState(
    var isLoading: Boolean = false,
    var syndicateServicesPaymentRequestUI: SyndicateServicesPaymentRequestUI? = null
)
