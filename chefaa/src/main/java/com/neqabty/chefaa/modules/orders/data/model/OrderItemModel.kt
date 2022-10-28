package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderItemModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("iamge")
    val image: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("product_id")
    val productId: Int?
)
