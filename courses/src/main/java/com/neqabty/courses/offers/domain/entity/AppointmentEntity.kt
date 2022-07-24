package com.neqabty.courses.offers.domain.entity

data class AppointmentEntity(
    val day: Int = 0,
    val id: Int = 0,
    val offer: Int = 0,
    val timeFrom: String = "",
    val timeTo: String = ""
)