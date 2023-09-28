package com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist


import androidx.annotation.Keep

@Keep
data class Item(
    val created_at: String,
    val discount_percentage: String,
    val id: Int,
    val imported: Int,
    val name: String,
    val order_id: Int,
    val price: Double,
    val price_before_discount: Int,
    val product_image: Any,
    val quantity: String,
    val updated_at: String
)