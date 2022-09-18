package com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse


data class DetailsEntity(
    val cardPrice: Double,
    val currentFeeYear: Double,
    val delayFine: Double,
    val lastFeeYear: Int,
    val lateSubscriptions: Double,
    val totalPrice: Double
)