package com.neqabty.trips.modules.destinations.domain.entity


data class FeatureEntity(
    val description: String = "",
    val destination: Int = 0,
    val id: Int = 0,
    val included: Boolean = false,
    val limit: Int = 0,
    val name: String = "",
    val price: Double = 0.0
)