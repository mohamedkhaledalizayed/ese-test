package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id")
    val id: Int,
    @SerializedName("entity")
    val entity: String,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("license_number")
    val license_number: String
)