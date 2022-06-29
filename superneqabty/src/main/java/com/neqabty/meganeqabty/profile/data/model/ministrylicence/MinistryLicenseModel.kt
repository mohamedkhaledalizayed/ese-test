package com.neqabty.meganeqabty.profile.data.model.ministrylicence


import com.google.gson.annotations.SerializedName

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