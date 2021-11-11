package com.neqabty.yodawy.modules.products.domain.entity

data class ProductEntity(
    val active: Boolean,
    val id: String,
    val image: Any,
    val isLimitedAvailability: Boolean,
    val isMedication: Boolean,
    val name: String,
    val outOfStock: Boolean,
    val regularPrice: Int,
    val salePrice: Int
)
