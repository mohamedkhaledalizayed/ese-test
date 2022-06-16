package com.neqabty.healthcare.modules.offers.domain.entity.offers



data class OffersEntity(
    val description: String,
    val expiryDate: String,
    val percentage: String,
    val title: String
)