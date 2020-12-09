package com.neqabty.domain.entities

data class MedicalRenewalPaymentEntity(
        var requestID: String = "",
        var paymentItem: PaymentItem? = null
) {
    data class PaymentItem(
            var paymentRequestNumber: String = "",
            var amount: Int?,
            var name: String?,
            var engName: String?,
            var engNumber: String?,
            var paymentDetailsItems: List<PaymentDetailsItem>? = null
    )

    data class PaymentDetailsItem(
            var name: String = "",
            var totalAmount: String = ""
    )
}