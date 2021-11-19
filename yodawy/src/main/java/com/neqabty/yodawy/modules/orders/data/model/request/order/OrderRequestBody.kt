package com.neqabty.yodawy.modules.orders.data.model.request.order

import com.google.gson.annotations.SerializedName

data class OrderRequestBody(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("OrderId")
    val orderId: String
)