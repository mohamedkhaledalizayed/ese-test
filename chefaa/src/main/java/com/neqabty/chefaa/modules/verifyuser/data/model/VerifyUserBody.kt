package com.neqabty.chefaa.modules.verifyuser.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VerifyUserBody(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("verification_code")
    val verificationCode: String
)