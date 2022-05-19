package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class PaymentHistoryData(
    @field:SerializedName("reference")
    var reference: String,
    @field:SerializedName("code")
    var code: String?,
    @field:SerializedName("amount")
    var amount: String,
    @field:SerializedName("status")
    var status: String,
    @field:SerializedName("getway")
    var gateway: String
)