package com.neqabty.presentation.ui.oldsignup

import com.neqabty.presentation.entities.UserUI

data class SignupViewState(
    var isLoading: Boolean = true,
    var user: UserUI? = null
)
