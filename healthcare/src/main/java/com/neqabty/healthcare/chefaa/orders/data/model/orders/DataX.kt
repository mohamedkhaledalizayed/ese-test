package com.neqabty.healthcare.chefaa.orders.data.model.orders


import androidx.annotation.Keep

@Keep
data class DataX(
    val address_id: Int,
    val addresses: Addresses,
    val chefaa_order_number: String,
    val client_id: String,
    val country_code: String,
    val created_at: String,
    val current_location: String,
    val delivery_fees: Any,
    val delivery_note: String,
    val device_info: String,
    val id: Int,
    val items: List<Item>,
    val order_client: OrderClient,
    val order_status: OrderStatus,
    val phone: String,
    val platform: String,
    val price: Any,
    val price_before_discount: Any,
    val status: Int,
    val updated_at: String,
    val user_plan: String
)