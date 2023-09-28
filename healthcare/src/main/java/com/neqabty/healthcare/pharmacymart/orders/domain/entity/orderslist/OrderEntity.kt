package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist



data class OrderEntity(
    val actual_delivery_datetime: String,
    val cancelled_by: String,
    val created_at: String,
    val delivery_fees: Int,
    val id: Int,
    val imported_discount_percentage: String,
    val local_discount_percentage: String,
    val mobile: String,
    val order_number: String,
    val order_status: OrderStatusEntity,
    val order_text: String,
    val paid: Int,
    val pharmacy_user_id: Int,
    val price_after_discount: String,
    val price_before_discount: String,
    val status: Int,
    val total_price: String,
)