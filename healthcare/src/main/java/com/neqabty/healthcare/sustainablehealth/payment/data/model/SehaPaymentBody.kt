package com.neqabty.healthcare.sustainablehealth.payment.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SehaPaymentBody(
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
    @SerializedName("delivery_method")
    val deliveryMethod: Int,
    @SerializedName("membership_id")
    val membershipId: Int = 0
)