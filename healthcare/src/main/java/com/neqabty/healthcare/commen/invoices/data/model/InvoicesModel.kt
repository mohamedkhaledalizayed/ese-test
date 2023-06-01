package com.neqabty.healthcare.commen.invoices.data.model


import androidx.annotation.Keep

@Keep
data class InvoicesModel(
    val account: Account,
    val created_at: String,
    val entity: String,
    val gateway_reference_id: String,
    val membership_id: String,
    val net_amount: String,
    val service_action: ServiceAction,
    val status: String,
    val total_amount: String,
    val total_fees: String
)