package com.neqabty.meganeqabty.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Receipt(
    @SerializedName("card_fees")
    val cardFees: Double,
    @SerializedName("card_total_price")
    val cardTotalPrice: Double,
    @SerializedName("code_fees")
    val codeFees: Double,
    @SerializedName("code_total_price")
    val codeTotalPrice: Double,
    @SerializedName("details")
    val details: Details
)