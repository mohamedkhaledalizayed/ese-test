package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Receipt(
    @SerializedName("last_fee_year")
    val lastFeeYear: Int,
    @SerializedName("current_fee_year")
    val currentFeeYear: Double,
    @SerializedName("card_price")
    val cardPrice: Double,
    @SerializedName("late_subscriptions")
    val lateSubscriptions: Double,
    @SerializedName("delay_fine")
    val delayFine: Double,
    @SerializedName("net_amount")
    val netAmount: Double,
    @SerializedName("fees")
    val fees: Double,
    @SerializedName("error")
    val error: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("total_amount")
    val totalPrice: Double
)