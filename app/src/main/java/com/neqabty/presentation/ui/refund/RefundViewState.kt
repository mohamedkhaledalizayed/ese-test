package com.neqabty.presentation.ui.refund

import com.neqabty.presentation.entities.*

data class RefundViewState(
    var isLoading: Boolean = true,
    var liteFollowersListUI: List<LiteFollowersListUI>? = null,
    var areas: List<AreaUI>? = null,
    var governs: List<GovernUI>? = null,
    var providerTypes: List<ProviderTypeUI>? = null,
    var providers: List<ProviderUI>? = null
)
