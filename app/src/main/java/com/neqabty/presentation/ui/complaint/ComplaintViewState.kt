package com.neqabty.presentation.ui.complaint

import com.neqabty.presentation.entities.ComplaintTypeUI

data class ComplaintViewState(
    var isLoading: Boolean = false,
    var types: List<ComplaintTypeUI>? = null,
    var message: String = ""
)
