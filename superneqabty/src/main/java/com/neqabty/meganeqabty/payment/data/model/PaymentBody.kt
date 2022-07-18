package com.neqabty.meganeqabty.payment.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
class PaymentBody (
    @SerializedName("payment")
    val payment: PaymentBodyObject
)

@Keep
class PaymentBodyObject(
    @SerializedName("service_code")
    val serviceCode: String = "",
    @SerializedName("service_action_code")
    val serviceActionCode: String = "",
    @SerializedName("payment_method")
    val paymentMethod: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("amount")
    val amount: String = "",
    @SerializedName("membership_id")
    val itemId: Int = 0
)