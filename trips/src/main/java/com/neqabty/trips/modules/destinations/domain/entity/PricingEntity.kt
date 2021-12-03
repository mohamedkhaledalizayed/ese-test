package com.neqabty.trips.modules.destinations.domain.entity

data class PricingEntity(
    val id: Int = 0,
    val periodEntity: PeriodEntity = PeriodEntity(),
    val periodPrice: Double = 0.0,
    val pricePer: String = "",
    val unitFeaturesClass: Int = 0
)