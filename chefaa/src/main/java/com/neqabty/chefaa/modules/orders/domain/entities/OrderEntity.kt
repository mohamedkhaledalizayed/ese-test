package com.neqabty.chefaa.modules.orders.domain.entities

data class OrderEntity(
    val id: String,
    val addressId: String,
    val deliveryNote: String,
    val creationDate: String,
    val status: String
)