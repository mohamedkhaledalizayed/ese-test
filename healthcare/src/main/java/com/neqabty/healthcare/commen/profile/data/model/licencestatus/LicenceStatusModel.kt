package com.neqabty.healthcare.commen.profile.data.model.licencestatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LicenceStatusModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivered")
    val delivered: String,
    @SerializedName("delivery_time")
    val deliveryTime: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("license")
    val license: String,
    @SerializedName("license_date")
    val licenseDate: Any,
    @SerializedName("member")
    val member: Member,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusMessage")
    val statusMessage: String,
    @SerializedName("unique_id")
    val uniqueId: String,
    @SerializedName("updated_at")
    val updatedAt: String
)