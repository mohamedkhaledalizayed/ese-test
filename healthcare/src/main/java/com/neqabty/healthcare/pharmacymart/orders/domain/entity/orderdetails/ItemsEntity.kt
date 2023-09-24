package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails



data class ItemsEntity(
    val discountPercentage: String,
    val id: Int,
    val imported: Int,
    val name: String,
    val orderId: Int,
    val price: String,
    val priceBeforeDiscount: Int,
    val productImage: String,
    val quantity: String,
)