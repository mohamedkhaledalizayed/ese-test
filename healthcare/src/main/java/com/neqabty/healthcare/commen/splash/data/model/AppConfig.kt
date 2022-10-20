package com.neqabty.healthcare.commen.splash.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class AppConfig(
    @SerializedName("api_configurations")
    val apiConfigurations: List<ApiConfiguration>
)