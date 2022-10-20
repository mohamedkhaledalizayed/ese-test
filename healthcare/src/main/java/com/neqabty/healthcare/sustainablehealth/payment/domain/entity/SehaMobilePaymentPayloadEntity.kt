package com.neqabty.healthcare.sustainablehealth.payment.domain.entity



data class SehaMobilePaymentPayloadEntity(
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