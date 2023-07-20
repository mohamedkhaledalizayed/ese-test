package com.neqabty.healthcare.auth.forgetpassword.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SendOTPBody(
    @SerializedName("mobile")
    val mobile: String
)