package com.neqabty.presentation.entities

data class UserUI(
        var mobile: String = "",
        var type: String?,
        var details: List<UserDetails>?
) {
    data class UserDetails(
            var name: String?,
            var userNumber: String?
    )
}