package com.neqabty.presentation.ui.chooseArea

import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI

data class ChooseAreaViewState(
        var isLoading: Boolean = false,
        var areas : List<AreaUI>? = null,
        var governs : List<GovernUI>? = null
)
