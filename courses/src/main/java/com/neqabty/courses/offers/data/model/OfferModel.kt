package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName
import com.neqabty.courses.home.data.model.CourseModel

data class OfferModel(
    @SerializedName("address")
    val address: String?,
    @SerializedName("appointments")
    val appointments: List<Appointment> = listOf(),
    @SerializedName("contact")
    val contact: String?,
    @SerializedName("course")
    val course: CourseModel,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("fully_booked")
    val fullyBooked: Boolean?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_available")
    val isAvailable: Boolean?,
    @SerializedName("links")
    val links: Links,
    @SerializedName("max_num_of_trainees")
    val maxNumOfTrainees: Int?,
    @SerializedName("num_of_trainees")
    val numOfTrainees: Int?,
    @SerializedName("pricing")
    val pricing: List<Pricing> = listOf(),
    @SerializedName("reservations")
    val reservations: List<Reservation> = listOf(),
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("title")
    val title: String?
)