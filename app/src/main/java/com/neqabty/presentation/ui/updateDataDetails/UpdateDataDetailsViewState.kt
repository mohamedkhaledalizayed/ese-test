package com.neqabty.presentation.ui.updateDataDetails

import com.neqabty.presentation.entities.InquireUpdateUserDataUI

data class UpdateDataDetailsViewState(
    var isLoading: Boolean = false,
    var code: String = "",
    var userDataInquire: InquireUpdateUserDataUI? = null
)
