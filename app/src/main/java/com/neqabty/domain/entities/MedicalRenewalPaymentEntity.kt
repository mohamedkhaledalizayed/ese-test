package com.neqabty.domain.entities

import com.google.gson.annotations.SerializedName

data class MedicalRenewalPaymentEntity(
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
        var totalAmount: Double?
    )

    data class AmountItem(
        @field:SerializedName("name")
        var name: String? = "",
        @field:SerializedName("getway_id")
        var id: Int,
        @field:SerializedName("card_amount")
        var cardAmount: Float?,
        @field:SerializedName("pos_amount")
        var posAmount: Float?
    )
}