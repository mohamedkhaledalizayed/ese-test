package com.neqabty.meganeqabty.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Service(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("require_cysr")
    val requireCysr: Boolean,
    @SerializedName("require_registration")
    val requireRegistration: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)