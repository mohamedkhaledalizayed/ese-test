package com.neqabty.domain.entities

data class UserEntity(
    var token: String = "",
    var type: String?,
    var name: String?
)