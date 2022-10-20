package com.neqabty.healthcare.mega.payment.domain.entity.inquiryresponse


import com.google.gson.annotations.SerializedName

data class DeliveryMethodsEntity(
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