package com.neqabty.healthcare.pharmacymart.orders.domain.entity.cancelorder



data class CancelOrderEntity(
    val data: String,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)