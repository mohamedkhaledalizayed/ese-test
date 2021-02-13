package com.neqabty.presentation.ui.signup

import com.neqabty.presentation.entities.UserUI

data class SignupViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var user: UserUI? = null
)
