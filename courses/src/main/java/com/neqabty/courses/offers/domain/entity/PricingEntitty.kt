package com.neqabty.courses.offers.domain.entity


data class PricingEntitty(
    val id: Int,
    val offer: Int,
    val price: String,
    val studentCategoryEntity: StudentCategoryEntity
)