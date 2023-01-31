package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
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
    @SerializedName("family")
    val family: String,
    @SerializedName("total_price")
    val totalPrice: Double
)