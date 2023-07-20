package com.neqabty.healthcare.payment.data.model.paymentstatus


import androidx.annotation.Keep

@Keep
data class PaymentStatusModel(
    val account: Account,
    val created_at: String,
    val entity: String,
    val gateway_reference_id: String,
    val membership_id: String,
    val net_amount: String,
    val service_action: ServiceAction,
    val total_amount: String,
    val total_fees: String
)