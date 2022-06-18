package com.neqabty.healthcare.modules.search.presentation.mappers

import com.neqabty.healthcare.modules.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.GovernorateEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.ProfessionEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.ProviderTypeEntity
import com.neqabty.healthcare.modules.search.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi

fun FiltersEntity.toFiltersUi(): FiltersUi{
    return FiltersUi(
        governorates = governorates.map { it.toGovernorateUi() },
        professions = professions.map { it.toProfessionUi() },
        providerTypes = providerTypes.map { it.toProviderTypeUi() }

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