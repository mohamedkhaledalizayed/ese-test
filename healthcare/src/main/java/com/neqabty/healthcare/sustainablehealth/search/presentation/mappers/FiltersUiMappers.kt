package com.neqabty.healthcare.sustainablehealth.search.presentation.mappers

import com.neqabty.healthcare.sustainablehealth.search.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.filter.*
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.ItemUi

fun FiltersEntity.toFiltersUi(): FiltersUi{
    return FiltersUi(
        governorates = governorates.map { it.toGovernorateUi() },
        professions = professions.map { it.toProfessionUi() },
        providerTypes = providerTypes.map { it.toProviderTypeUi() },
        degrees = degrees.map { it.toDegreeUi() }

    )
}

fun AreaListEntity.toAreaListUi(): ItemUi{
    return ItemUi(
        id = id,
        name = areaName
    )
}

private fun GovernorateEntity.toGovernorateUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

private fun ProfessionEntity.toProfessionUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

private fun ProviderTypeEntity.toProviderTypeUi(): ItemUi{
    return ItemUi(
        id = id,
        name = name
    )
}

private fun DegreeEntity.toDegreeUi(): ItemUi{
    return ItemUi(
        id = id,
        name = degreeName
    )
}