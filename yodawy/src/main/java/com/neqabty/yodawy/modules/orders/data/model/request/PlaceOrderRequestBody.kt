package com.neqabty.yodawy.modules.orders.data.model.request


import com.google.gson.annotations.SerializedName

data class PlaceOrderRequestBody(
    @SerializedName("AddressId")
    val addressId: String,
    @SerializedName("Items")
    val itemRequests: List<ItemRequest>,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("Notes")
    val notes: String,
    @SerializedName("Plan")
    val plan: String
)