package com.neqabty.presentation.ui.claiming

import com.neqabty.presentation.entities.*

data class ClaimingViewState(
        var isLoading: Boolean = false,
        var doctors : List<DoctorUI>? = null,
        var areas : List<AreaUI>? = null,
        var degrees : List<DegreeUI>? = null,
        var specializations : List<SpecializationUI>? = null,
        var providerTypes : List<ProviderTypeUI>? = null,
        var providers : List<ProviderUI>? = null,
        var member : MemberUI? = null
)
