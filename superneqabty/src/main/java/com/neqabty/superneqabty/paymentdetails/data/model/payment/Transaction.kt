package com.neqabty.superneqabty.paymentdetails.data.model.payment


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("account")
    val account: Int,
    @SerializedName("cashier_url")
    val cashierUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
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
    val gatewayReferenceId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("item_id")
    val itemId: Int,
    @SerializedName("mobile")
    val mobile: Any,
    @SerializedName("net_amount")
    val netAmount: String,
    @SerializedName("payment_gateway_reference_id")
    val paymentGatewayReferenceId: String,
    @SerializedName("payment_method")
    val paymentMethod: Int,
    @SerializedName("payment_request_number")
    val paymentRequestNumber: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("portal_owner_fees")
    val portalOwnerFees: String,
    @SerializedName("provider_fees")
    val providerFees: String,
    @SerializedName("service_action")
    val serviceAction: String,
    @SerializedName("total_amount")
    val totalAmount: String,
    @SerializedName("total_fees")
    val totalFees: String,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)