package com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class Feature(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("require_registration")
    val requireRegistration: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String
)