package com.neqabty.domain.entities

data class PaymentHistoryEntity(
    var reference: String,
    var code: String,
    var amount: String,
    var status: String,
    var gateway: String
)