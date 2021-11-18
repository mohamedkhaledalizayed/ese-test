package com.neqabty.yodawy.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
    @SerializedName("Id")
    val id: String,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean,
    @SerializedName("Message")
    val message: String
)