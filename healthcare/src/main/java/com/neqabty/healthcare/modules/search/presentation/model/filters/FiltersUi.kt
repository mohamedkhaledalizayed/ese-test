package com.neqabty.healthcare.modules.search.presentation.model.filters



data class FiltersUi(
    val governorates: List<ItemUi>,
    val professions: List<ItemUi>,
    val providerTypes: List<ItemUi>
)