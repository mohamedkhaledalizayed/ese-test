package com.neqabty.presentation.ui.changeNumber

import com.neqabty.presentation.entities.UserUI

data class ChangeNumberViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var user: UserUI? = null
)
