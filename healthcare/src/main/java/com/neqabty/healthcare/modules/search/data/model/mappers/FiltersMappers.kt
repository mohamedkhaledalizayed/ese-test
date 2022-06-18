package com.neqabty.healthcare.modules.search.data.model.mappers

import com.neqabty.healthcare.modules.search.data.model.filter.Governorate
import com.neqabty.healthcare.modules.search.data.model.filter.Profession
import com.neqabty.healthcare.modules.search.data.model.filter.FiltersModel
import com.neqabty.healthcare.modules.search.data.model.filter.ProviderType
import com.neqabty.healthcare.modules.search.domain.entity.filter.GovernorateEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.ProfessionEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.ProviderTypeEntity

fun FiltersModel.toFiltersEntity(): FiltersEntity {
    return FiltersEntity(
        governorates = governorates.map { it.toGovEntity() },
        professions = professions.map { it.toProEntity() },
        providerTypes = providerTypes.map { it.toProviderTypeEntity() }
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