package com.neqabty.healthcare.chefaa.home.domain.entities.orders


import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity

data class DataEntity(
    val current_page: Int,
    val `data`: List<OrderEntity>,
    val first_page_url: String,
    val from: Int,
    val next_page_url: Any?,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any?,
    val to: Int
)