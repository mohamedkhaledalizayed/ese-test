package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CourseReservation(
    @SerializedName("contact_phone")
    val contactPhone: String,
    @SerializedName("cost")
    val cost: Double,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("queue_number")
    val queueNumber: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("student")
    val student: Int
)