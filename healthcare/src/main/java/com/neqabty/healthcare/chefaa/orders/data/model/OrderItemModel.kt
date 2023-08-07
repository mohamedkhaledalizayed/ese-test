package com.neqabty.healthcare.chefaa.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderItemModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("product_id")
    val productId: Int?
)
