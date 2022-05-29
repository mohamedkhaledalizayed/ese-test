package com.neqabty.presentation.ui.refundRequest

import com.neqabty.presentation.entities.*

data class RefundRequestViewState(
    var isLoading: Boolean = true,
    var refundUI: RefundUI? = null
)
