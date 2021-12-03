package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
)