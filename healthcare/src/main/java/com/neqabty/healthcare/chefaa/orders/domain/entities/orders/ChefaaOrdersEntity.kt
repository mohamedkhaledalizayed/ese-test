package com.neqabty.healthcare.chefaa.orders.domain.entities.orders


data class ChefaaOrdersEntity(
    val `data`: DataEntity?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)