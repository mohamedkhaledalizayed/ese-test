package com.neqabty.healthcare.mega.payment.domain.entity.payment



data class PaymentEntity(
    val address: String,
    val cashierUrl: String,
    val deliveryFees: String,
    val deliveryMethod: Int,
    val entityRefNum: Any,
    val fees: String,
    val id: String,
    val membershipId: String,
    val paymentGateway: Int,
    val paymentGatewayTransactionNum: String,
    val paymentMethod: String,
    val paymentSource: String,
    val refund: Boolean,
    val service: String,
    val serviceAction: String,
    val status: String,
    val totalAmount: String,
    val transactionType: String,
    val publicKey: String,
    val callBackURL: String,
    val expireAt: String,
    val reference: String,
    val merchantId: String
    )