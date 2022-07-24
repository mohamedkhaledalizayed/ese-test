package com.neqabty.courses.offers.domain.entity

data class OfferInReservationEntity(
    val address: String = "",
    val author: Int = 0,
    val contact: String = "",
    val course: Int = 0,
    val createdAt: String = "",
    val endDate: String = "",
    val groupNumber: String = "",
    val id: Int = 0,
    val isAvailable: Boolean = false,
    val isConfirmable: Boolean = false,
    val maxNumOfTrainees: Int = 0,
    val startDate: String = "",
    val title: String = "",
    val updatedAt: String = ""
)