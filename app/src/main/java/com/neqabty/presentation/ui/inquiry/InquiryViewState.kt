package com.neqabty.presentation.ui.inquiry

import com.neqabty.presentation.entities.MemberUI

data class InquiryViewState(
        var isLoading: Boolean = false,
        var member: MemberUI? = null
)
