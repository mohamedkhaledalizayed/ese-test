package com.neqabty.healthcare.chefaa.home.data.model


import androidx.annotation.Keep

@Keep
data class OrderModel(
    val address_id: Int,
    val addresses: Addresses,
    val chefaa_order_number: String,
    val client_id: String,
    val country_code: String,
    val created_at: String,
    val current_location: String,
    val delivery_fees: Float?,
    val delivery_note: String,
    val device_info: String,
    val id: Int,
    val items: List<Item>,
    val order_client: OrderClient,
    val order_status: OrderStatus,
    val phone: String,
    val platform: String,
    val price: Float?,
    val price_before_discount: Float?,
    val status: Int,
    val updated_at: String,
    val user_plan: String
)