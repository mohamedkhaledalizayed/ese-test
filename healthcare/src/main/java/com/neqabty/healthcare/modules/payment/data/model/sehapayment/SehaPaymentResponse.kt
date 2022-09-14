package com.neqabty.healthcare.modules.payment.data.model.sehapayment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SehaPaymentResponse(
    @SerializedName("mobile_payment_payload")
    val mobilePaymentPayload: SehaMobilePaymentPayloadModel?,
    @SerializedName("payment")
    val payment: SehaPaymentModel
)