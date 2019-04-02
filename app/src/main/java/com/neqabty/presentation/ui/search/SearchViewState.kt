package com.neqabty.presentation.ui.search

import com.neqabty.presentation.entities.*

data class SearchViewState(
        var isLoading: Boolean = false,
        var areas : List<AreaUI>? = null,
        var governs : List<GovernUI>? = null,
        var degrees : List<DegreeUI>? = null,
        var professions : List<SpecializationUI>? = null,
        var providerTypes : List<ProviderTypeUI>? = null
)
