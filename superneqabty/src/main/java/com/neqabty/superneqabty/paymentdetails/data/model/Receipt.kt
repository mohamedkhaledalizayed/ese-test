package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("details")
    val details: List<Detail>,
    @SerializedName("total_price")
    val totalPrice: Double
)