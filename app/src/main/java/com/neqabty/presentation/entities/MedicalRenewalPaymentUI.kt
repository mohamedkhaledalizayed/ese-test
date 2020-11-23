package com.neqabty.presentation.entities

data class MedicalRenewalPaymentUI(
        var requestID: String = "",
        var paymentItem: PaymentItem? = null
) {
    data class PaymentItem(
            var paymentRequestNumber: String = "",
            var amount: Int?,
            var name: String?,
            var paymentDetailsItems: List<PaymentDetailsItem>? = null
    )

    data class PaymentDetailsItem(
            var name: String = "",
            var totalAmount: String = ""
    )
}