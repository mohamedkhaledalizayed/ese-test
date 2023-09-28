package com.neqabty.healthcare.pharmacymart.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequestBody(
    @SerializedName("mobile") val userNumber: String,
    @SerializedName("order_id") val orderId: String
)
