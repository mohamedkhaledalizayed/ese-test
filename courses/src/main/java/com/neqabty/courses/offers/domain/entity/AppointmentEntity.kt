package com.neqabty.courses.offers.domain.entity


import com.google.gson.annotations.SerializedName

data class AppointmentEntity(
    val day: Int,
    val id: Int,
    val offer: Int,
    val timeFrom: String,
    val timeTo: String
)