package com.neqabty.signup.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class SignupBody(
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("licence_number")
    val licenceNumber: String = "",
    @SerializedName("membership_id")
    val membershipId: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = "",
    @SerializedName("password")
    val password: String = ""
)