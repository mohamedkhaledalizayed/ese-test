package com.neqabty.presentation.ui.changePassword

data class ChangePasswordViewState(
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var msg: String = ""
)
