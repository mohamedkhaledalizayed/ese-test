package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("delivery_note")
    val deliveryNote: String,
    @SerializedName("created_at")
    val creationDate: String,
    @SerializedName("status")
    val status: String
)