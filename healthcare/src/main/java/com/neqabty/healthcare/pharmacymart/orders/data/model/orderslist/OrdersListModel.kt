package com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist


import androidx.annotation.Keep

@Keep
data class OrdersListModel(
    val `data`: OrderObject?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)