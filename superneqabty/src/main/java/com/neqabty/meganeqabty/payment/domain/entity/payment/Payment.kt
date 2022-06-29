package com.neqabty.meganeqabty.payment.domain.entity.payment


data class Payment(
    val amount: String,
    val id: Int,
    val itemId: Int,
    val paymentMethod: String,
    val paymentSource: String,
    val serviceCode: String,
    val transaction: TransactionEntity,
)