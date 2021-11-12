package com.neqabty.yodawy.modules.order.data.model.request


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Quantity")
    val quantity: Int
)