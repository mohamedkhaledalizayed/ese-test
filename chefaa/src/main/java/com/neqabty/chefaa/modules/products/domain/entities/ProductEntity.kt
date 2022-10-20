package com.neqabty.chefaa.modules.products.domain.entities

data class ProductEntity(
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val titleAr: String = "",
    val titleEn: String = "",
    val type: String = ""
)
