package com.neqabty.healthcare.payment.data.model.payment


import androidx.annotation.Keep

@Keep
data class PaymentModel(
    val address: String?,
    val branch: Any,
    val cancelUrl: Any,
    val cashier_url: String?,
    val created_at: String,
    val delivery_fees: String?,
    val delivery_method: Int?,
    val entity_ref_num: String?,
    val fees: String?,
    val id: String,
    val membership_id: String?,
    val merchant_id: String?,
    val message: String?,
    val net_amount: String?,
    val payment_gateway: Int?,
    val payment_gateway_transaction_num: String?,
    val payment_method: String?,
    val payment_source: String?,
    val public_key: String?,
    val reference: String?,
    val referenceCode: String?,
    val refund: Boolean,
    val returnUrl: Any,
    val service: String,
    val service_action: String,
    val status: String?,
    val total_amount: String?,
    val transaction_type: String?,
    val callBackURL: String?,
    val expireAt: String?,
    val updated_at: String
)