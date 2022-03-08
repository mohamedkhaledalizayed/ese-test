package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("details")
    val details: List<Detail>,
    @SerializedName("total_price")
    val totalPrice: Double,
    @SerializedName("card_fees")
    val card_fees: Double,
    @SerializedName("card_total_price")
    val card_total_price: Double,
    @SerializedName("outlet_fees")
    val outlet_fees: Double,
    @SerializedName("outlet_total_price")
    val outlet_total_price: Double,
)