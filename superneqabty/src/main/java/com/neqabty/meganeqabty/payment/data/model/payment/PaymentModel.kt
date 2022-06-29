package com.neqabty.meganeqabty.payment.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentModel(
    @SerializedName("account")
    val account: Any,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("callback_fail_url")
    val callbackFailUrl: Any,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("item_id")
    val itemId: Int,
    @SerializedName("message")
    val message: Any,
    @SerializedName("mobile")
    val mobile: Any,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("status")
    val status: Any,
    @SerializedName("transaction")
    val transaction: Transaction,
    @SerializedName("updated_at")
    val updatedAt: String
)