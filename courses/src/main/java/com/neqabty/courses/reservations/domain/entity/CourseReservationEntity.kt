package com.neqabty.courses.reservations.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CourseReservationEntity(
    val contactPhone: String,
    val cost: Double,
    val courseName: String,
    val image: String,
    val notes: String?,
    val offer: Int,
    val paymentStatus: String,
    val queueNumber: Int,
    val status: String,
    val student: Int
): Parcelable