package com.neqabty.healthcare.packages.payment.domain.entity.paymentmethods



data class DeliveryEntity(
    val id: Int,
    val method: String,
    val price: Double,
    val type: String,
)