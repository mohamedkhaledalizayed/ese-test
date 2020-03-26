package com.neqabty.presentation.ui.inquiry

import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.ServiceUI

data class InquiryViewState(
    var isLoading: Boolean = true,
    var services: List<ServiceUI>? = null,
    var member: MemberUI? = null
)
