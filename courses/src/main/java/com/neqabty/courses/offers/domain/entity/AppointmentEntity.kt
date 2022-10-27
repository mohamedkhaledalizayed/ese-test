package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppointmentEntity(
    val day: Int = 0,
    val id: Int = 0,
    val offer: Int = 0,
    val timeFrom: String = "",
    val dayName: String = "",
    val timeTo: String = ""
) : Parcelable