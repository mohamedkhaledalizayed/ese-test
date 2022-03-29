package com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("total")
    val total: Double
)