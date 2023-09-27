package com.neqabty.healthcare.pharmacymart.orders.domain.entity.confirmorder



data class ConfirmOrderEntity(
    val data: String,
    val message: String,
    val status: Boolean,
    val status_code: Int
)