package com.neqabty.meganeqabty.payment.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class DeliveryMethod(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("updated_at")
    val updatedAt: String
)