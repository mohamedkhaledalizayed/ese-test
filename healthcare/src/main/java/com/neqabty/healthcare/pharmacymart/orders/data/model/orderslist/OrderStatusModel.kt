package com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist


import androidx.annotation.Keep

@Keep
data class OrderStatusModel(
    val created_at: String?,
    val id: Int,
    val title_ar: String?,
    val title_en: String?,
    val updated_at: String?
)