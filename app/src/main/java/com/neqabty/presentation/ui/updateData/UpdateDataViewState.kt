package com.neqabty.presentation.ui.updateData

import com.neqabty.presentation.entities.InquireUpdateUserDataUI

data class UpdateDataViewState(
    var isLoading: Boolean = false,
    var userDataInquire: InquireUpdateUserDataUI? = null
)
