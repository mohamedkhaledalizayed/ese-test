package com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("card_price")
    val cardPrice: Double,
    @SerializedName("current_fee_year")
    val currentFeeYear: Double,
    @SerializedName("delay_fine")
    val delayFine: Double,
    @SerializedName("last_fee_year")
    val lastFeeYear: Int,
    @SerializedName("late_subscriptions")
    val lateSubscriptions: Double,
    @SerializedName("total_price")
    val totalPrice: Double
)