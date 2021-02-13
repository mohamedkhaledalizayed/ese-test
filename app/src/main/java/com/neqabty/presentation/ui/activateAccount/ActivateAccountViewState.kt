package com.neqabty.presentation.ui.activateAccount

import com.neqabty.presentation.entities.UserUI

data class ActivateAccountViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var user: UserUI? = null
)
