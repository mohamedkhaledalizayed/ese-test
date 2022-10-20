package com.neqabty.healthcare.commen.profile.data.model.ministrylicence


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class MinistryLicenseModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("license")
    val license: String,
    @SerializedName("member")
    val member: MemberData,
    @SerializedName("statusMessage")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)