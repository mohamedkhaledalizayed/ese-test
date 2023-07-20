package com.neqabty.healthcare.commen.packages.payment.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Payment(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("service_action_code")
    val serviceActionCode: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("transaction_type")
    val transactionType: String
)