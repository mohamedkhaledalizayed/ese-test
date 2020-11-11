package com.neqabty.domain.entities

data class MedicalRenewalPaymentEntity(
        var requestID: String = "",
        var paymentItem: PaymentItem? = null
) {
    data class PaymentItem(
            var paymentRequestNumber: String = "",
            var amount: Int?,
            var name: String?
    )
}