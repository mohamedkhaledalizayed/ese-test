package com.neqabty.presentation.entities

data class MemberUI(
        var engineerID: Int = 0,
        var engineerName: String?,
        var expirationDate: String?,
        var paymentType: String?,
        var billDate: String?,
        var code: Int?,
        var interfaceLanguage: String?,
        var lastPaymentDate: String?,
        var message: String?,
        var amount: String?
)