package com.neqabty.shealth.sustainablehealth.payment.domain.entity


data class SehaPaymentEntity(
    val mobilePaymentPayload: SehaMobilePaymentPayloadEntity?,
    val payment: SehaPayment
)