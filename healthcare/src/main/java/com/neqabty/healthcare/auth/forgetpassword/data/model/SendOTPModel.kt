package com.neqabty.healthcare.auth.forgetpassword.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SendOTPModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)