package com.neqabty.healthcare.commen.packages.payment.domain.entity.paymentmethods



data class PaymentEntity(
    val deliveryMethods: DeliveryEntity,
    val paymentMethods: List<PaymentMethodEntity> = listOf()
)