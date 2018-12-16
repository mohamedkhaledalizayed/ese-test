package com.neqabty.presentation.ui.claiming

import com.neqabty.presentation.entities.UserUI

data class ClaimingViewState(
        var isLoading: Boolean = true,
        var request : UserUI? = null
)
