package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("day")
    val day: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("time_from")
    val timeFrom: String,
    @SerializedName("time_to")
    val timeTo: String
)