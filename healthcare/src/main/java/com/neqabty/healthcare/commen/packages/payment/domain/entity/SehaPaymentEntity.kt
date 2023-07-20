package com.neqabty.healthcare.commen.packages.payment.domain.entity


data class SehaPaymentEntity(
    val mobilePaymentPayload: SehaMobilePaymentPayloadEntity?,
    val payment: SehaPayment
)