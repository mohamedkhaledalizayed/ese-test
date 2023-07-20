package com.neqabty.healthcare.commen.packages.payment.data.model.sehapayment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SehaTransactionModel(
    @SerializedName("account")
    val account: Int,
    @SerializedName("cashier_url")
    val cashierUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivered")
    val delivered: Boolean,
    @SerializedName("delivery")
    val delivery: Any,
    @SerializedName("entity")
    val entity: Int,
    @SerializedName("entity_fees")
    val entityFees: String,
    @SerializedName("entity_payment_status")
    val entityPaymentStatus: Any,
    @SerializedName("entity_ps_txt")
    val entityPsTxt: String,
    @SerializedName("gateway_fees")
    val gatewayFees: String,
    @SerializedName("gateway_payment_status")
    val gatewayPaymentStatus: Any,
    @SerializedName("gateway_ps_txt")
    val gatewayPsTxt: String,
    @SerializedName("gateway_reference_id")
    val gatewayReferenceId: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("member_name")
    val memberName: Any,
    @SerializedName("membership_id")
    val membershipId: Any,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("net_amount")
    val netAmount: String,
    @SerializedName("payment_gateway_reference_id")
    val paymentGatewayReferenceId: String?,
    @SerializedName("payment_method")
    val paymentMethod: Int,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("portal_owner_fees")
    val portalOwnerFees: String,
    @SerializedName("provider_fees")
    val providerFees: String,
    @SerializedName("service_action")
    val serviceAction: String,
    @SerializedName("shipping_fees")
    val shippingFees: String,
    @SerializedName("total_amount")
    val totalAmount: String,
    @SerializedName("total_fees")
    val totalFees: String,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)