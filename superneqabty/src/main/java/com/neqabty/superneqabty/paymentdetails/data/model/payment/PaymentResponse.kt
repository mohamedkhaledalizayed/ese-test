package com.neqabty.superneqabty.paymentdetails.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("payment")
    val payment: Payment
)