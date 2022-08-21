package com.neqabty.meganeqabty.payment.domain.entity.payment


data class PaymentEntity(
    val mobilePaymentPayload: MobilePaymentPayloadEntity?,
    val payment: Payment
)