package com.neqabty.healthcare.packages.payment.domain.entity


data class SehaPaymentEntity(
    val mobilePaymentPayload: SehaMobilePaymentPayloadEntity?,
    val payment: SehaPayment
)