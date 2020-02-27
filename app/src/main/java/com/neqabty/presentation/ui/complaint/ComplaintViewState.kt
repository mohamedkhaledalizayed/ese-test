package com.neqabty.presentation.ui.complaint

import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.entities.GovernUI

data class ComplaintViewState(
    var isLoading: Boolean = false,
    var types: List<ComplaintTypeUI>? = null
)
