package com.neqabty.healthcare.sustainablehealth.payment.data.model.paymentmethods


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PaymentMethodsResponse(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethodModel> = listOf()
)