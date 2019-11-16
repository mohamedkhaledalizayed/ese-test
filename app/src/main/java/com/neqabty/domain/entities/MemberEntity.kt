package com.neqabty.domain.entities

data class MemberEntity(
    var engineerID: String = "",
    var engineerName: String?,
    var expirationDate: String?,
    var paymentType: String?,
    var billDate: String?,
    var code: Int?,
    var interfaceLanguage: String?,
    var lastPaymentDate: String?,
    var message: String?,
    var amount: Int?
)