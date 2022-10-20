package com.neqabty.healthcare.sustainablehealth.payment.domain.entity


data class SehaPaymentEntity(
    val mobilePaymentPayload: SehaMobilePaymentPayloadEntity?,
    val payment: SehaPayment
)