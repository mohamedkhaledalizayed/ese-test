package com.neqabty.signup.modules.verifyphonenumber.data.model.recaptcha


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VerifyRecaptcha(
    @SerializedName("apk_package_name")
    val apkPackageName: String,
    @SerializedName("challenge_ts")
    val challengeTs: String,
    @SerializedName("success")
    val success: Boolean
)