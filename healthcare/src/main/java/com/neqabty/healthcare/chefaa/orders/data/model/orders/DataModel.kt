package com.neqabty.healthcare.chefaa.orders.data.model.orders


import androidx.annotation.Keep
import com.neqabty.healthcare.chefaa.orders.data.model.OrderModel

@Keep
data class DataModel(
    val current_page: Int,
    val data: List<OrderModel>,
    val first_page_url: String,
    val from: Int,
    val next_page_url: Any,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Int
)