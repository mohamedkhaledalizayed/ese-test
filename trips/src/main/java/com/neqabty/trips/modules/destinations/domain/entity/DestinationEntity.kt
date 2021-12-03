package com.neqabty.trips.modules.destinations.domain.entity

import com.neqabty.trips.modules.home.domain.entity.CityEntity

data class DestinationEntity(
    val address: String = "",
    val city: CityEntity,
    val description: String = "",
    val featureEntities: List<FeatureEntity> = listOf(),
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val notes: List<String> = listOf(),
    val unitFeaturesClasses: List<UnitFeaturesClassEntity> = listOf()
)