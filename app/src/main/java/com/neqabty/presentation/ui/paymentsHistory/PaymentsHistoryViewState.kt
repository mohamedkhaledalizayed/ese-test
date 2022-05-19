package com.neqabty.presentation.ui.paymentsHistory

import com.neqabty.presentation.entities.PaymentHistoryUI

data class PaymentsHistoryViewState(
    var isLoading: Boolean = true,
    var payments: List<PaymentHistoryUI>? = null
)
