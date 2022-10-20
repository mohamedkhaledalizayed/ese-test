package com.neqabty.healthcare.mega.payment.domain.entity.payment



data class MobilePaymentPayloadEntity(
    val callbackUrl: String,
    val countryCode: String,
    val currency: String,
    val expireAt: Int,
    val merchantId: String,
    val merchantName: String,
    val payAmount: String,
    val paymentType: String,
    val productDescription: String,
    val productName: String,
    val publickey: String,
    val reference: String,
    val userClientIP: String
)