package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Repetition(
    @SerializedName("every")
    val every: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("period")
    val period: Int = 0
)