package com.neqabty.trips.modules.destinations.data.model.mappers

import com.neqabty.trips.modules.destinations.data.model.*
import com.neqabty.trips.modules.destinations.domain.entity.*
import com.neqabty.trips.modules.home.data.model.mappers.toCityEntity

fun DestinationModel.toDestinationEntity(): DestinationEntity {
    return DestinationEntity(
        address,
        city = city.toCityEntity(),
        description,
        featureEntities = features.map { it.toFeatureEntity() },
        id,
        image,
        name,
        notes,
        unitFeaturesClasses = unitFeaturesClasses.map { it.toUnitFeaturesEntity() }
    )
}

fun UnitFeaturesClasse.toUnitFeaturesEntity(): UnitFeaturesClassEntity {
    return UnitFeaturesClassEntity(
        categoryEntities = categories.map { it.toCategoryEntity() },
        destination, id, name, numOfFlats, personNo, pricingEntities = pricings.map { it.toPricingEntity() }
    )
}

private fun Pricing.toPricingEntity():PricingEntity {
    return PricingEntity(id, periodEntity = period.toPeriodEntity(),periodPrice, pricePer, unitFeaturesClass)
}

private fun Period.toPeriodEntity(): PeriodEntity {
    return PeriodEntity(endDate, id, name)
}

private fun Category.toCategoryEntity():CategoryEntity {
    return CategoryEntity(id, name)
}

fun Feature.toFeatureEntity(): FeatureEntity {
    return FeatureEntity(description, destination, id, included, limit, name, price)
}
