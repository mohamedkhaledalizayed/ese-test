package com.neqabty.healthcare.mega.payment.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
class PaymentNaturalBody (
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
    @SerializedName("address")
    val address: String = "",
    @SerializedName("branch")
    val branch: String = "",
    @SerializedName("membership_id")
    val membershipId: String = "202022303",
    @SerializedName("delivery_method")
    val deliveryMethod: Int,
    @SerializedName("delivery_mobile")
    val deliveryMobile: String = "",
    @SerializedName("delivery_notes")
    val deliveryNotes: String = ""
)