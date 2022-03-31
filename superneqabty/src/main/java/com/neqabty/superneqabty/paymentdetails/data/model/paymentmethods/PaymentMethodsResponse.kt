package com.neqabty.superneqabty.paymentdetails.data.model.paymentmethods


import com.google.gson.annotations.SerializedName

data class PaymentMethodsResponse(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>
)