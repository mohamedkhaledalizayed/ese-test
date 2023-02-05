package com.neqabty.healthcare.mega.payment.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
class PaymentBody (
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
    @SerializedName("payment_gateway")
    val paymentGateway: Int,
    @SerializedName("membership_id")
    val membershipId: Int = 0,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: String = "http://ex.com",
    @SerializedName("callback_fail_url")
    val callbackFailUrl: String = "http://ex2.com"
)

