package com.neqabty.healthcare.commen.packages.payment.data.model.paymentmethods


import com.google.gson.annotations.SerializedName

data class DeliveryMethod(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("method_id")
    val methodId: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)