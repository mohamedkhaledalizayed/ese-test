package com.neqabty.presentation.ui.login

import com.neqabty.presentation.entities.UserUI

data class LoginViewState(
    var isLoading: Boolean = false,
    var user: UserUI? = null
)
