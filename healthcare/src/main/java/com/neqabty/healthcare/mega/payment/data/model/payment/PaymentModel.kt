package com.neqabty.healthcare.mega.payment.data.model.payment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PaymentModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("branch")
    val branch: Any,
    @SerializedName("callback_fail_url")
    val callbackFailUrl: String,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: String,
    @SerializedName("cashier_url")
    val cashierUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivery_fees")
    val deliveryFees: String,
    @SerializedName("delivery_method")
    val deliveryMethod: Int,
    @SerializedName("entity_ref_num")
    val entityRefNum: Any,
    @SerializedName("fees")
    val fees: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("membership_id")
    val membershipId: String,
    @SerializedName("message")
    val message: Any,
    @SerializedName("net_amount")
    val netAmount: String,
    @SerializedName("payment_gateway")
    val paymentGateway: Int,
    @SerializedName("payment_gateway_transaction_num")
    val paymentGatewayTransactionNum: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("refund")
    val refund: Boolean,
    @SerializedName("service")
    val service: String,
    @SerializedName("service_action")
    val serviceAction: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_amount")
    val totalAmount: String,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)