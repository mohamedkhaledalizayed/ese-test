package com.neqabty.domain.entities

data class UserEntity(
        var mobile: String = "",
        var type: String?,
        var details: List<UserDetails>?
) {
    data class UserDetails(
            var name: String?,
            var userNumber: String?
    )
}