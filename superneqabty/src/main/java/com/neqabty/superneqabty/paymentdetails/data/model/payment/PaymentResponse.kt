package com.neqabty.superneqabty.paymentdetails.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("mobile_payment_payload")
    val mobilePaymentPayload: MobilePaymentPayload,
    @SerializedName("payment")
    val payment: Payment
)