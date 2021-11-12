package com.neqabty.yodawy.modules.order.data.model.request


import com.google.gson.annotations.SerializedName

data class PlaceOrderRequestBody(
    @SerializedName("AddressId")
    val addressId: String,
    @SerializedName("Items")
    val items: List<Item>,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("Notes")
    val notes: String,
    @SerializedName("Plan")
    val plan: String
)