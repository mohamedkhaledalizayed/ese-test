package com.neqabty.healthcare.pharmacymart.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderListRequestBody(
    @SerializedName("mobile") val userNumber: String
)
