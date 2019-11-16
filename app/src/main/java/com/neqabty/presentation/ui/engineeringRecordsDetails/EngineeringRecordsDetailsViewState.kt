package com.neqabty.presentation.ui.engineeringRecordsDetails

import com.neqabty.presentation.entities.RegisteryUI

data class EngineeringRecordsDetailsViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var memberItem: RegisteryUI? = null
)
