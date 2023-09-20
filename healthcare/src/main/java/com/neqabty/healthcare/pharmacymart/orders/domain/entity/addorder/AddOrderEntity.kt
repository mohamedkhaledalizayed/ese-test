package com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder


data class AddOrderEntity(
    val status: Boolean = false,
    val statusCode: Int = 0,
    val message: String = ""
)