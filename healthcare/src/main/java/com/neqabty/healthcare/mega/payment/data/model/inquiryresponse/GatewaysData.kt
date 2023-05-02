package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GatewaysData(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("endpoint_url")
    val endpointUrl: String,
    @SerializedName("gateway")
    val gateway: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)