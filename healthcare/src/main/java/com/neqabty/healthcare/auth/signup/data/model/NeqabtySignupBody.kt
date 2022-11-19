package com.neqabty.healthcare.auth.signup.data.model


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
    @SerializedName("password")
    val password: String
)