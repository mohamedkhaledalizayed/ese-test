package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Member(
    @SerializedName("account")
    val account: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("entity")
    val entity: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_fee_year")
    val lastFeeYear: Int,
    @SerializedName("license_end_date")
    val licenseEndDate: String,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("national_id")
    val nationalId: Long,
    @SerializedName("updated_at")
    val updatedAt: String
)