package com.neqabty.healthcare.auth.otp.data.model.sendotp


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OTPModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("phone_number")
    val phoneNumber: String
)