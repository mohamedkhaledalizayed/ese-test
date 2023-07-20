package com.neqabty.healthcare.auth.signup.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignUpAgriBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("entity_code")
    val entityCode: String,
    @SerializedName("membership_id")
    val membershipId: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("password")
    val password: String
)