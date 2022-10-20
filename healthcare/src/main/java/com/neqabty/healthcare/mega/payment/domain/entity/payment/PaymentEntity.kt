package com.neqabty.healthcare.mega.payment.domain.entity.payment


data class PaymentEntity(
    val mobilePaymentPayload: MobilePaymentPayloadEntity?,
    val payment: Payment
)