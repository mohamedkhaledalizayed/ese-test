package com.neqabty.healthcare.sustainablehealth.receipt.data.model.paymentstatus


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class PaymentStatusModel(
    @SerializedName("account")
    val account: Account,
    @SerializedName("cashier_url")
    val cashierUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("entity")
    val entity: String,
    @SerializedName("entity_fees")
    val entityFees: String,
    @SerializedName("entity_payment_status")
    val entityPaymentStatus: Boolean?,
    @SerializedName("entity_ps_txt")
    val entityPsTxt: String,
    @SerializedName("gateway_fees")
    val gatewayFees: String,
    @SerializedName("gateway_payment_status")
    val gatewayPaymentStatus: Boolean?,
    @SerializedName("gateway_ps_txt")
    val gatewayPsTxt: String?,
    @SerializedName("gateway_reference_id")
    val gatewayReferenceId: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("member_name")
    val memberName: Any,
    @SerializedName("membership_id")
    val membershipId: Int?,
    @SerializedName("mobile")
    val mobile: Any,
    @SerializedName("net_amount")
    val netAmount: String,
    @SerializedName("payment_gateway_reference_id")
    val paymentGatewayReferenceId: Any,
    @SerializedName("payment_method")
    val paymentMethod: PaymentMethod,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("portal_owner_fees")
    val portalOwnerFees: String,
    @SerializedName("provider_fees")
    val providerFees: String,
    @SerializedName("service_action")
    val serviceAction: ServiceAction,
    @SerializedName("total_amount")
    val totalAmount: String,
    @SerializedName("total_fees")
    val totalFees: String,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)