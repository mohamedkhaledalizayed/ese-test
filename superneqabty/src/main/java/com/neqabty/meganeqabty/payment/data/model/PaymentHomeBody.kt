package com.neqabty.meganeqabty.payment.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
class PaymentHomeBody (
    @SerializedName("payment")
    val payment: PaymentHomeBodyObject
)

@Keep
class PaymentHomeBodyObject(
    @SerializedName("service_code")
    val serviceCode: String = "",
    @SerializedName("service_action_code")
    val serviceActionCode: String = "",
    @SerializedName("payment_method")
    val paymentMethod: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("delivery_method")
    val deliveryMethod: Int = 1,
    @SerializedName("membership_id")
    val membershipId: Int = 0
)