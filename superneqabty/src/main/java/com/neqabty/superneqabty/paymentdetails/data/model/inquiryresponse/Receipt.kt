package com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("card_fees")
    val cardFees: String,
    @SerializedName("card_total_price")
    val cardTotalPrice: String,
    @SerializedName("details")
    val details: List<Detail>,
    @SerializedName("outlet_fees")
    val outletFees: String,
    @SerializedName("outlet_total_price")
    val outletTotalPrice: String,
    @SerializedName("total_price")
    val totalPrice: String
)