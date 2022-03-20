package com.neqabty.superneqabty.paymentdetails.data.model.payment


import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("account")
    val account: Int,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("callback_fail_url")
    val callbackFailUrl: String,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("item_id")
    val itemId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("mobile")
    val mobile: Any,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("transaction")
    val transaction: Transaction,
    @SerializedName("updated_at")
    val updatedAt: String
)