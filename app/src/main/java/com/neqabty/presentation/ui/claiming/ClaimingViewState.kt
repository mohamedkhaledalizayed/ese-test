package com.neqabty.presentation.ui.claiming

import com.neqabty.presentation.entities.*

data class ClaimingViewState(
        var isLoading: Boolean = false,
        var areas : List<AreaUI>? = null,
        var governs : List<GovernUI>? = null,
        var providerTypes : List<ProviderTypeUI>? = null,
        var providers : List<ProviderUI>? = null,
        var member : MemberUI? = null
)
