package com.neqabty.presentation.ui.syndicates

import com.neqabty.presentation.entities.UserUI

data class SyndicatesViewState(
        var isLoading: Boolean = true,
        var user : UserUI? = null
)
