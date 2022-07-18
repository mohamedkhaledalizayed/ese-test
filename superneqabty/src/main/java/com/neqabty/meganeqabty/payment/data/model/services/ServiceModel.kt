package com.neqabty.meganeqabty.payment.data.model.services


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServiceModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("require_cysr")
    val requireCysr: Boolean,
    @SerializedName("type")
    val type: String
)