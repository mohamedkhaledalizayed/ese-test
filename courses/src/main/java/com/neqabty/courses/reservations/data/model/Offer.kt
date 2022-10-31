package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Offer(
    @SerializedName("address")
    val address: String,
    @SerializedName("appointments")
    val appointments: List<Appointment>,
    @SerializedName("author")
    val author: Author,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("course")
    val course: Course,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("fully_booked")
    val fullyBooked: Boolean,
    @SerializedName("group_number")
    val groupNumber: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    @SerializedName("links")
    val links: Links,
    @SerializedName("max_num_of_trainees")
    val maxNumOfTrainees: Int,
    @SerializedName("num_of_trainees")
    val numOfTrainees: Int,
    @SerializedName("pricings")
    val pricings: List<Pricing>,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("title")
    val title: String
)