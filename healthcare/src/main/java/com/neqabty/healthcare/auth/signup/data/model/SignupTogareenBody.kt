package com.neqabty.healthcare.auth.signup.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SignupTogareenBody(
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("membership_id")
    val membershipId: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("serial_number")
    val serialNumber: String = ""
)