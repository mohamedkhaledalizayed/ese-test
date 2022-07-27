package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferEntity(
    val address: String = "",
    val appointmentEntities: List<AppointmentEntity> = listOf(),
    val authorEntity: AuthorEntity = AuthorEntity(),
    val contact: String = "",
    val courseInOfferEntity: CourseInOfferEntity = CourseInOfferEntity(),
    val endDate: String = "",
    val fullyBooked: Boolean = false,
    val groupNumber: String = "",
    val id: Int = 0,
    val isAvailable: Boolean = false,
    val maxNumOfTrainees: Int = 0,
    val numOfTrainees: Int = 0,
    val pricingEntities: List<PricingEntity> = listOf(),
    val reservationEntities: List<ReservationEntity> = listOf(),
    val startDate: String = "",
    val title: String = ""
) : Parcelable