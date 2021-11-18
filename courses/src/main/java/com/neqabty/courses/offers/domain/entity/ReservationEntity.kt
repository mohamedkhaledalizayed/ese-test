package com.neqabty.courses.offers.domain.entity


data class ReservationEntity(
    val cost: String,
    val id: Int,
    val offer: Int,
    val paymentStatus: Any?,
    val queueNumber: Int,
    val status: String,
    val student: StudentEntity
)