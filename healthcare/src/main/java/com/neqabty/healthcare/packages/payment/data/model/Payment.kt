package com.neqabty.healthcare.packages.payment.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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