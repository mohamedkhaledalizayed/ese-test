package com.neqabty.healthcare.packages.payment.data.model.paymentmethods


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentMethodsResponse(
    @SerializedName("delivery_methods")
    val deliveryMethods: DeliveryMethod,
    @SerializedName("PaymentGateway")
    val paymentMethods: List<PaymentMethodModel> = listOf()
)