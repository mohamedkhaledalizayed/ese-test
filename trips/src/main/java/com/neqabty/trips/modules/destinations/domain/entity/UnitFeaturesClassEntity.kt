package com.neqabty.trips.modules.destinations.domain.entity


data class UnitFeaturesClassEntity(
    val categoryEntities: List<CategoryEntity> = listOf(),
    val destination: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val numOfFlats: Int = 0,
    val personNo: Int = 0,
    val pricingEntities: List<PricingEntity> = listOf(),
    val roomsNo: Int = 0
)