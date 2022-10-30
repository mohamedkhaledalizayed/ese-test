package com.neqabty.healthcare.sustainablehealth.splash.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ApiConfiguration(
    @SerializedName("android_version")
    val androidVersion: String
)