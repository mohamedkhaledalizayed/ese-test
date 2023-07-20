package com.neqabty.healthcare.chefaa.verifyuser.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyUserBody(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("verification_code")
    val verificationCode: String
)