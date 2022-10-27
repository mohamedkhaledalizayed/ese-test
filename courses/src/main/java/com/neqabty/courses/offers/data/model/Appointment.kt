package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("day")
    val day: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("offer")
    val offer: Int = 0,
    @SerializedName("time_from")
    val timeFrom: String = "",
    @SerializedName("day_name")
    val dayName: String = "",
    @SerializedName("time_to")
    val timeTo: String = ""
)