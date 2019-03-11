package com.neqabty.presentation.ui.syndicates

import com.neqabty.presentation.entities.SyndicateUI

data class SyndicatesViewState(
        var isLoading: Boolean = true,
        var syndicates : List<SyndicateUI>? = null
)
