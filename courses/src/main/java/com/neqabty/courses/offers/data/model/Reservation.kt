package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Reservation(
    @SerializedName("cost")
    val cost: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("offer")
    val offer: Int,
    @SerializedName("payment_status")
    val paymentStatus: Any? = null,
    @SerializedName("queue_number")
    val queueNumber: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("student")
    val student: Student
)