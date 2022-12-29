package com.neqabty.healthcare.auth.forgetpassword.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CheckOTPModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String
)