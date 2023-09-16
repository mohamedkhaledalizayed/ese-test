package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist



data class OrdersEntityList(
    val data: List<OrderEntity>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)