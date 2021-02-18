package com.neqabty.presentation.ui.loginWithPassword

import com.neqabty.presentation.entities.UserUI

data class LoginWithPasswordViewState(
    var isLoading: Boolean = false,
    var user: UserUI? = null,
    var msg: String = ""
)
