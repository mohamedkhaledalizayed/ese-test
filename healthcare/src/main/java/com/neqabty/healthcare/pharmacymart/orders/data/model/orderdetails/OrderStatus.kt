package com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails


import androidx.annotation.Keep

@Keep
data class OrderStatus(
    val created_at: Any,
    val id: Int,
    val title_ar: String,
    val title_en: String,
    val updated_at: Any
)