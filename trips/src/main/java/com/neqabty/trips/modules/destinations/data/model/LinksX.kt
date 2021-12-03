package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("categories")
    val categories: String = "",
    @SerializedName("pricings")
    val pricings: String = ""
)