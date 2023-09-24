package com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails


import androidx.annotation.Keep

@Keep
data class OrderItemModel(
    val actual_delivery_datetime: String?,
    val address_id: Int,
    val attachments: List<String>,
    val awb_number: Any,
    val cancellation_reason: String?,
    val cancelled_by: String?,
    val country_code: Any,
    val created_at: String?,
    val delivery_fees: String?,
    val delivery_mobile: String?,
    val delivery_note: String?,
    val id: Int,
    val imported_discount_percentage: String?,
    val items: List<ItemsModel>,
    val local_discount_percentage: String?,
    val mobile: String,
    val order_number: String,
    val order_status: OrderStatus,
    val order_text: String?,
    val paid: Int,
    val pharmacy_user_id: Int,
    val platform: String,
    val price_after_discount: String?,
    val price_before_discount: String?,
    val status: Int,
    val total_price: String?,
    val updated_at: String,
    val updated_by: Int
)