package com.neqabty.domain.entities

data class MemberEntity(
    var requestID: String = "",
    var engineerName: String?,
    var amount: Int?,
    var msg: String?,
    var payments: List<PaymentItem>? = null
){
    data class PaymentItem(
            var quantity: Int = 0,
            var totalPrice: String?,
            var name: String?
    )
}