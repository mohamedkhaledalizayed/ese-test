package com.neqabty.superneqabty.splash.data.model


import com.google.gson.annotations.SerializedName

data class AppConfig(
    @SerializedName("api_configurations")
    val apiConfigurations: List<ApiConfiguration>
)