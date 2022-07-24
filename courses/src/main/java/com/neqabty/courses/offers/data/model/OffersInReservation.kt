package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class OffersInReservation(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("author")
    val author: Int = 0,
    @SerializedName("contact")
    val contact: String = "",
    @SerializedName("course")
    val course: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("end_date")
    val endDate: String = "",
    @SerializedName("group_number")
    val groupNumber: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_available")
    val isAvailable: Boolean = false,
    @SerializedName("is_confirmable")
    val isConfirmable: Boolean = false,
    @SerializedName("max_num_of_trainees")
    val maxNumOfTrainees: Int = 0,
    @SerializedName("start_date")
    val startDate: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)