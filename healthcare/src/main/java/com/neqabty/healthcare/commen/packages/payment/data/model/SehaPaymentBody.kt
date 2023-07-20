package com.neqabty.healthcare.commen.packages.payment.data.model


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
    @SerializedName("delivery_method")
    val deliveryMethod: Int = 0,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("transaction_type")
    val transactionType: String = "payment",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("delivery_mobile")
    val deliveryMobile: String = "",
    @SerializedName("delivery_notes")
    val deliveryNotes: String = ""
)