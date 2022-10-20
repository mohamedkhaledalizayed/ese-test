package com.neqabty.mega.payment.domain.entity.paymentmethods



data class PaymentMethodEntity(
    val displayName: String,
    val gateway: String,
    val id: Int,
    val isActive: Boolean,
    val name: String
)