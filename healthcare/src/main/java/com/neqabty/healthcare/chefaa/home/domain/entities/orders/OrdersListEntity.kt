package com.neqabty.healthcare.chefaa.home.domain.entities.orders

data class OrdersListEntity(
    val `data`: DataEntity?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)