package com.neqabty.healthcare.medicalnetwork.presentation.model.filters



data class FiltersUi(
    val governorates: List<ItemUi>,
    val professions: List<ItemUi>,
    val providerTypes: List<ItemUi>,
    val degrees: List<ItemUi>
)