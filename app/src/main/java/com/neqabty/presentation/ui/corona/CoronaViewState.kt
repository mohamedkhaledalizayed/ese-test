package com.neqabty.presentation.ui.corona

import com.neqabty.presentation.entities.SyndicateUI

data class CoronaViewState(
    var isLoading: Boolean = false,
    var message: String = "",
    var syndicates: List<SyndicateUI>? = null
)
