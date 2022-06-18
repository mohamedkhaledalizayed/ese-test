package com.neqabty.healthcare.modules.search.domain.entity.filter



data class FiltersEntity(
    val governorates: List<GovernorateEntity>,
    val professions: List<ProfessionEntity>,
    val providerTypes: List<ProviderTypeEntity>
)