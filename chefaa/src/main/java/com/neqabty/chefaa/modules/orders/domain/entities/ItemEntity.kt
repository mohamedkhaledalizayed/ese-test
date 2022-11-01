package com.neqabty.chefaa.modules.orders.domain.entities

data class ItemEntity(
    val addressId: String = "",
    val chefaaOrderNumber: String = "",
    val clientId: String = "",
    val createdAt: String = "",
    val id: Int = 0,
    val note: String = "",
    val orderId: Int = 0,
    val productId: String = "",
    val quantity: String = "",
    val type: String = "",
    val updatedAt: String = ""
)
