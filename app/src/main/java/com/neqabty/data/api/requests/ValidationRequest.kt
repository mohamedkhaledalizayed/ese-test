package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ValidationRequest(
    @SerializedName("engineerID")
    var syndicateId: Int = 0,
    @SerializedName("paymentType")
    var paymentType: Int = 2
) : Request()