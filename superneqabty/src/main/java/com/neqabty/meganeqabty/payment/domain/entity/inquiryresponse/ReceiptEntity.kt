package com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse



data class ReceiptEntity(
    val cardFees: Double,
    val cardTotalPrice: Double,
    val codeFees: Double,
    val codeTotalPrice: Double,
    val details: DetailsEntity
)