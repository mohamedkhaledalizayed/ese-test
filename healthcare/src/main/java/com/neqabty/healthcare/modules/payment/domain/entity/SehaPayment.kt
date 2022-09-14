package com.neqabty.healthcare.modules.payment.domain.entity


data class SehaPayment(
    val amount: String,
    val id: Int,
    val itemId: Int?,
    val paymentMethod: String,
    val paymentSource: String,
    val serviceCode: String,
    val transaction: SehaTransactionEntity,
)