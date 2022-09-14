package com.neqabty.healthcare.modules.payment.domain.entity


data class SehaPaymentEntity(
    val mobilePaymentPayload: SehaMobilePaymentPayloadEntity?,
    val payment: SehaPayment
)