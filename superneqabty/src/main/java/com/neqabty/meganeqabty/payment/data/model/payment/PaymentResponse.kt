package com.neqabty.meganeqabty.payment.data.model.payment


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PaymentResponse(
    @SerializedName("mobile_payment_payload")
    val mobilePaymentPayload: MobilePaymentPayload,
    @SerializedName("payment")
    val payment: PaymentModel
)