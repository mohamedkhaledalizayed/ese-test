package com.neqabty.presentation.ui.subsyndicates

import com.neqabty.presentation.entities.SyndicateUI

data class SubSyndicatesViewState(
        var isLoading: Boolean = true,
        var subSyndicates : List<SyndicateUI>? = null
)
