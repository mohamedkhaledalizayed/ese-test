package com.neqabty.healthcare.auth.forgetpassword.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CheckOTPBody(
    @SerializedName("otp")
    val otp: Int
)