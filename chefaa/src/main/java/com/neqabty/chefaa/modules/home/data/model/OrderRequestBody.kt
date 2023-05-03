package com.neqabty.chefaa.modules.home.data.model

import com.google.gson.annotations.SerializedName

data class OrderRequestBody(
    @SerializedName("order_id")
    val orderId: String
)