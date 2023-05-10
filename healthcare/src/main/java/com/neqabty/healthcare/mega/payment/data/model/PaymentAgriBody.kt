package com.neqabty.healthcare.mega.payment.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
class PaymentAgriBody (
    @SerializedName("service")
    val serviceCode: String = "",
    @SerializedName("service_action")
    val serviceActionCode: String = "",
    @SerializedName("payment_method")
    val paymentMethod: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("transaction_type")
    val transactionType: String = "payment",
    @SerializedName("membership_id")
    val membershipId: Int = 0,
    @SerializedName("delivery_mobile")
    val deliveryMobile: String = "",
    @SerializedName("delivery_notes")
    val deliveryNotes: String = ""
)