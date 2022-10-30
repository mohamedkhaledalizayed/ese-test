package com.neqabty.shealth.sustainablehealth.search.data.model.mappers

import com.neqabty.shealth.sustainablehealth.search.data.model.filter.*
import com.neqabty.shealth.sustainablehealth.search.domain.entity.filter.*

fun FiltersModel.toFiltersEntity(): FiltersEntity {
    return FiltersEntity(
        governorates = governorates.map { it.toGovEntity() },
        professions = professions.map { it.toProEntity() },
        providerTypes = providerTypes.map { it.toProviderTypeEntity() },
        degrees = degrees.map { it.toDegreeEntity() }
    )
}

private fun Profession.toProEntity(): ProfessionEntity {
    return ProfessionEntity(
        id = id,
        name = professionName,
    )
}

private fun Governorate.toGovEntity(): GovernorateEntity {
    return GovernorateEntity(
        name = governorateAr,
        id = id
    )
}

private fun ProviderType.toProviderTypeEntity(): ProviderTypeEntity {
    return ProviderTypeEntity(
        id = id,
        name = providerTypeAr
    )
}

private fun Degree.toDegreeEntity(): DegreeEntity{
    return DegreeEntity(
        degreeName = degreeName,
        id = id
    )
}