package com.neqabty.domain.entities

import com.neqabty.data.api.Response

data class PaymentRequestEntity(
    var netAmount: Double? = 0.0,
    var amount: Double? = 0.0,
    var refId: String
) : Response()