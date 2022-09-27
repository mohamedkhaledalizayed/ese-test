package com.neqabty.presentation.entities

import com.neqabty.data.api.Response

data class PaymentRequestUI(
    var netAmount: Double? = 0.0,
    var amount: Double? = 0.0,
    var refId: String
) : Response()