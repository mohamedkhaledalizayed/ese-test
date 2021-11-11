package com.neqabty.yodawy.modules.address.data.model


import com.google.gson.annotations.SerializedName

data class GeoLocationModel(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)