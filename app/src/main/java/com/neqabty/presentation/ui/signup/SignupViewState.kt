package com.neqabty.presentation.ui.signup

import com.neqabty.presentation.entities.UserUI

data class SignupViewState(
        var isLoading: Boolean = true,
        var user : UserUI? = null
)
