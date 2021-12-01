package com.neqabty.trips.modules.home.data.model

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)