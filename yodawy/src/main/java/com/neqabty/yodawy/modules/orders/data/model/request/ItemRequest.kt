package com.neqabty.yodawy.modules.orders.data.model.request


import com.google.gson.annotations.SerializedName

data class ItemRequest(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Quantity")
    val quantity: Int
)