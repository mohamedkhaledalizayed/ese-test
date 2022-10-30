package com.neqabty.shealth.auth.signup.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class NeqabtySignupBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("password")
    val password: String
)