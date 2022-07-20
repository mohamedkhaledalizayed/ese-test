package com.neqabty.healthcare.modules.verifyphone.data.model.sendotp


import com.google.gson.annotations.SerializedName

data class OTPModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("phone_number")
    val phoneNumber: String
)