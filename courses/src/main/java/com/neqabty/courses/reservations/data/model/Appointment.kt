package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Appointment(
    @SerializedName("day")
    val day: Int,
    @SerializedName("day_name")
    val dayName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("time_from")
    val timeFrom: String,
    @SerializedName("time_to")
    val timeTo: String
)