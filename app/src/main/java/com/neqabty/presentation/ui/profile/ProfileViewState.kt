package com.neqabty.presentation.ui.profile

import com.neqabty.presentation.entities.ProfileUI

data class ProfileViewState(
    var isLoading: Boolean = false,
    var profile: ProfileUI? = null
)
