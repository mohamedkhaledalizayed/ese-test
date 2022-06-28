package com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods


import com.google.gson.annotations.SerializedName

data class PaymentMethodsResponse(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>
)