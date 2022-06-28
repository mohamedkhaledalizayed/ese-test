package com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse


import com.google.gson.annotations.SerializedName

data class ReceiptResponse(
    @SerializedName("member")
    val member: Member,
    @SerializedName("receipt")
    val receipt: Receipt,
    @SerializedName("service")
    val service: Service,
    @SerializedName("title")
    val title: String
)