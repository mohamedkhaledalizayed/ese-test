package com.neqabty.courses.offers.domain.entity

data class ReservationEntity(
    val cashUrl: String = "",
    val createdAt: String = "",
    val id: Int = 0,
    val offerInReservationEntity: OfferInReservationEntity = OfferInReservationEntity(),
    val paymentStatus: String = "",
    val queueNumber: Int = 0,
    val status: String = "",
    val studentEntity: StudentEntity = StudentEntity(),
    val transactionId: String = "",
    val updatedAt: String = ""
)