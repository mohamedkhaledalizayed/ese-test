package com.neqabty

import com.neqabty.presentation.entities.UserUI

data class MainViewState(
    var isLoading: Boolean = false,
    var user: UserUI? = null
)