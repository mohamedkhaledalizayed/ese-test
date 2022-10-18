package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class SyndicateServicesPaymentRequestData(
    @field:SerializedName("net_amount")
    var netAmount: Double? = 0.0,
    @field:SerializedName("amount")
    var amount: Double? = 0.0,
    @field:SerializedName("ese_refrence_id")
    var refId: String
) : Response()