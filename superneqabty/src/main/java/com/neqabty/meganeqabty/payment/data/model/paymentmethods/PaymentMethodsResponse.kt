package com.neqabty.meganeqabty.payment.data.model.paymentmethods


import com.google.gson.annotations.SerializedName

data class PaymentMethodsResponse(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethodModel> = listOf()
)