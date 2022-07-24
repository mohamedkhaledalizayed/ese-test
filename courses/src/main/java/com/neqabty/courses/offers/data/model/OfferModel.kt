package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class OfferModel(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("appointments")
    val appointments: List<Appointment> = listOf(),
    @SerializedName("author")
    val author: Author = Author(),
    @SerializedName("contact")
    val contact: String = "",
    @SerializedName("course")
    val courseInOffer: CourseInOffer = CourseInOffer(),
    @SerializedName("end_date")
    val endDate: String = "",
    @SerializedName("fully_booked")
    val fullyBooked: Boolean = false,
    @SerializedName("group_number")
    val groupNumber: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_available")
    val isAvailable: Boolean = false,
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("max_num_of_trainees")
    val maxNumOfTrainees: Int = 0,
    @SerializedName("num_of_trainees")
    val numOfTrainees: Int = 0,
    @SerializedName("pricings")
    val pricings: List<Pricing> = listOf(),
    @SerializedName("reservations")
    val reservations: List<Reservation> = listOf(),
    @SerializedName("start_date")
    val startDate: String = "",
    @SerializedName("title")
    val title: String = ""
)