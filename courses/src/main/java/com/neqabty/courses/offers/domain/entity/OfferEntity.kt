package com.neqabty.courses.offers.domain.entity

import com.neqabty.courses.home.domain.entity.CourseEntity

data class OfferEntity(
    val address: String,
    val appointmentEntities: List<AppointmentEntity>,
    val contact: String,
    val endDate: String,
    val fullyBooked: Boolean,
    val id: Int,
    val isAvailable: Boolean,
    val maxNumOfTrainees: Int,
    val numOfTrainees: Int,
    val pricingEntitty: List<PricingEntitty>,
    val reservationEntities: List<ReservationEntity>,
    val startDate: String,
    val title: String
)