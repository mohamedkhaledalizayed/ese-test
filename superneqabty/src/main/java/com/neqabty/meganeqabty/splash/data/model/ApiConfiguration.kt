package com.neqabty.meganeqabty.splash.data.model


import com.google.gson.annotations.SerializedName

data class ApiConfiguration(
    @SerializedName("android_version")
    val androidVersion: String
)