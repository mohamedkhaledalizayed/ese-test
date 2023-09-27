package com.neqabty.domain.entities

data class SyndicateServicesPaymentEntity(
        var resultType: String = "",
        var requestID: String = "",
        var msg: String? = "",
        var paymentItem: PaymentItem? = null,
        var amounts: List<AmountItem>? = null
) {
    data class PaymentItem(
            var paymentRequestNumber: String? = "",
            var amount: Double?,
            var name: String?,
            var engName: String?,
            var engNumber: String?,
            var paymentDetailsItems: List<PaymentDetailsItem>? = null
    )

    data class PaymentDetailsItem(
            var name: String = "",
            var totalAmount: String = ""
    )

    data class AmountItem(
        var name: String? = "",
        var id: Int,
        var cardAmount: Float?,
        var posAmount: Float?
    )
}