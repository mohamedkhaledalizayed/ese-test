package com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails


import androidx.annotation.Keep

@Keep
data class OrderDetailsModel(
    val `data`: List<OrderItemModel>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)