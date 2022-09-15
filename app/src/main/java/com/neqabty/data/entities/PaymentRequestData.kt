package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class PaymentRequestData(
    @field:SerializedName("amount")
    var amount: Double? = 0.0,
    @field:SerializedName("details")
    var details: List<DetailsItem>? = null
) : Response() {
    data class DetailsItem(
        @field:SerializedName("name")
        var name: String? = "",
        @field:SerializedName("TotalPrice")
        var totalPrice: Double?
    )
}