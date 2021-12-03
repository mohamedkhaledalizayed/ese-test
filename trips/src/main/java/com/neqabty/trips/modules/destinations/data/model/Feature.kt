package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Feature(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("destination")
    val destination: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("included")
    val included: Boolean = false,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("price")
    val price: Double = 0.0
)