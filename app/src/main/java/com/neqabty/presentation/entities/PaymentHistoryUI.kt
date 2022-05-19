package com.neqabty.presentation.entities

data class PaymentHistoryUI(
    var reference: String,
    var code: String,
    var amount: String,
    var status: String,
    var gateway: String
)