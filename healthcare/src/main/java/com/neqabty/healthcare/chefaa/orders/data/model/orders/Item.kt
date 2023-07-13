package com.neqabty.healthcare.chefaa.orders.data.model.orders


import androidx.annotation.Keep

@Keep
data class Item(
    val address_id: String,
    val chefaa_order_number: String,
    val client_id: String,
    val created_at: String,
    val id: Int,
    val note: Any,
    val order_id: Int,
    val product_id: String,
    val product_image: Any,
    val product_name: Any,
    val quantity: String,
    val type: String,
    val updated_at: String
)