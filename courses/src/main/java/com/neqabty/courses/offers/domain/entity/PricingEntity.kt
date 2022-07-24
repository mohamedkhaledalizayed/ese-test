package com.neqabty.courses.offers.domain.entity

data class PricingEntity(
    val id: Int = 0,
    val offer: Int = 0,
    val price: String = "",
    val serviceActionCode: String = "",
    val serviceCode: String = "",
    val studentCategoryEntity: StudentCategoryEntity = StudentCategoryEntity()
)