package com.neqabty.healthcare.payment.domain.entity.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeliveryMethodsEntity(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("updated_at")
    val updatedAt: String
)