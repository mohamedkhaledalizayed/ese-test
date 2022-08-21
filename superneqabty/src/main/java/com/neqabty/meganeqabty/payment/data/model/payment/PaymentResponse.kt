package com.neqabty.meganeqabty.payment.data.model.payment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PaymentResponse(
    @SerializedName("mobile_payment_payload")
    val mobilePaymentPayload: MobilePaymentPayload?,
    @SerializedName("payment")
    val payment: PaymentModel
)