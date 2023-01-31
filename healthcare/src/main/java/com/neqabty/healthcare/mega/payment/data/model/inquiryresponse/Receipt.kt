package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Receipt(
    @SerializedName("wallet_fees")
    val walletFees: Double,
    @SerializedName("wallet_total_price")
    val walletTotalPrice: Double,
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