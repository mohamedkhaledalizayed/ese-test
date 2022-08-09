package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AreaBody(
    @SerializedName("zone")
    val zone: Zone
)

@Keep
data class Zone(
    @SerializedName("name")
    val name: String
)