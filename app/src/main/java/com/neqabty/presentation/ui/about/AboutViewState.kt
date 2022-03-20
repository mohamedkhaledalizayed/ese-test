package com.neqabty.presentation.ui.about

import com.neqabty.presentation.entities.SyndicateBranchUI
import com.neqabty.presentation.entities.SyndicateUI

data class AboutViewState(
    var isLoading: Boolean = true,
    var syndicate: SyndicateUI? = null,
    var branches: List<SyndicateBranchUI>? = null
)
