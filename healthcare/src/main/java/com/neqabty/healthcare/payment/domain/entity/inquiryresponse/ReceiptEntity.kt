package com.neqabty.healthcare.payment.domain.entity.inquiryresponse



data class ReceiptEntity(
    val lastFeeYear: Int,
    val currentFeeYear: Double,
    val cardPrice: Double,
    val lateSubscriptions: Double,
    val delayFine: Double,
    val netAmount: Double,
    val fees: Double,
    val totalPrice: Double
)