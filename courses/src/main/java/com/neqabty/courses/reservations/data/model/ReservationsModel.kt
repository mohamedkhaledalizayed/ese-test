package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ReservationsModel(
    @SerializedName("course_reservations")
    val courseReservations: List<CourseReservation>
)