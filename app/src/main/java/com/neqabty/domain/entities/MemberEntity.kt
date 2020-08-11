package com.neqabty.domain.entities

data class MemberEntity(
    var requestID: String = "",
    var engineerName: String = "",
    var engineerNumber: String = "",
    var amount: Int = 0,
    var msg: String = "",
    var payments: List<PaymentItem>? = null,
    var paymentCreationRequest: PaymentCreationRequest? = null
) {
    data class PaymentItem(
        var quantity: Int = 0,
        var totalPrice: String?,
        var name: String?
    )

    data class PaymentCreationRequest(
        var sender: Sender? = null,
        var senderRequestNumber: String?,
        var serviceCode: String?,
        var settlementAmounts: SettlementAmounts?,
        var currency: String?,
        var requestExpiryDate: String?,
        var userUniqueIdentifier: String?,
        var publicKey: String?
    )

    data class Sender(
        var id: String = "",
        var name: String?,
        var password: String?
    )

    data class SettlementAmounts(
        var settlementAccountCode: String = "",
        var amount: Int = 0
    )
}