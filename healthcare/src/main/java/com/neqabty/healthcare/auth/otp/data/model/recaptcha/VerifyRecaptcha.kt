package com.neqabty.healthcare.auth.otp.data.model.recaptcha


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyRecaptcha(
    @SerializedName("apk_package_name")
    val apkPackageName: String,
    @SerializedName("challenge_ts")
    val challengeTs: String,
    @SerializedName("success")
    val success: Boolean
)