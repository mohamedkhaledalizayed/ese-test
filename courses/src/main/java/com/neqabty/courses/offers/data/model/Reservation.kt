package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Reservation(
    @SerializedName("cash_url")
    val cashUrl: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("offer")
    val offersInReservation: OffersInReservation = OffersInReservation(),
    @SerializedName("payment_status")
    val paymentStatus: String = "",
    @SerializedName("queue_number")
    val queueNumber: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("student")
    val student: Student = Student(),
    @SerializedName("transaction_id")
    val transactionId: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)