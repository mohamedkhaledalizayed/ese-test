package com.neqabty.presentation.ui.mobile

import com.neqabty.presentation.entities.UserUI

data class MobileViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var user: UserUI? = null
)
