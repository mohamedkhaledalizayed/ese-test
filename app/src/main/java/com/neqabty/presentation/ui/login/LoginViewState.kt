package com.neqabty.presentation.ui.login

import com.neqabty.presentation.entities.UserUI

data class LoginViewState(
    var isLoading: Boolean = true,
    var user: UserUI? = null
)
