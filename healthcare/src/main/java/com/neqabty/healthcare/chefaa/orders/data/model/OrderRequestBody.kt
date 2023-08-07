package com.neqabty.healthcare.chefaa.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequestBody(
    @SerializedName("order_id")
    val orderId: String
)