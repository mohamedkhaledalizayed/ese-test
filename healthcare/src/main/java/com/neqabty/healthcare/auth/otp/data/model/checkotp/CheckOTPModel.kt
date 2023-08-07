package com.neqabty.healthcare.auth.otp.data.model.checkotp


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckOTPModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("verified")
    val verified: Boolean
)