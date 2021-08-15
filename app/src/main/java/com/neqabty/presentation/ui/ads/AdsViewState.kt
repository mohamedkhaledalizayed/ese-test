package com.neqabty.presentation.ui.ads

import com.neqabty.presentation.entities.AdUI

data class AdsViewState(
    var isLoading: Boolean = true,
    var ad: AdUI? = null
)
