package com.neqabty.healthcare.auth.otp.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CheckOTPBody(
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("phone_number")
    val phoneNumber: String
)