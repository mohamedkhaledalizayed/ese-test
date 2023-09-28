package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails



data class OrderDetailsEntity(
    val data: List<OrderItemEntity>,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)