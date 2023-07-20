package com.neqabty.healthcare.splash.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ApiConfiguration(
    @SerializedName("android_version")
    val androidVersion: String
)