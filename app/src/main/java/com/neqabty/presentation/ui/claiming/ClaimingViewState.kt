package com.neqabty.presentation.ui.claiming

import com.neqabty.presentation.entities.*

data class ClaimingViewState(
        var isLoading: Boolean = true,
        var doctors : List<DoctorUI>? = null,
        var areas : List<AreaUI>? = null,
        var degrees : List<DegreeUI>? = null,
        var specializations : List<SpecializationUI>? = null,
        var providerTypes : List<SpecializationUI>? = null,
        var providers : List<ProviderUI>? = null
)
