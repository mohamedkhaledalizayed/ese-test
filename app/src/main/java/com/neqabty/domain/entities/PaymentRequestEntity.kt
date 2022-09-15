package com.neqabty.domain.entities

import com.neqabty.data.api.Response

data class PaymentRequestEntity(
    var amount: Double? = 0.0,
    var details: List<DetailsItem>? = null
) : Response() {
    data class DetailsItem(
        var name: String? = "",
        var totalPrice: Double?
    )
}