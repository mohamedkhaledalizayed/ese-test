package com.neqabty.presentation.ui.engineeringRecordsInquiry

import com.neqabty.presentation.entities.RegisteryUI

data class EngineeringRecordsInquiryViewState(
    var isLoading: Boolean = false,
    var memberItem: RegisteryUI? = null
)
