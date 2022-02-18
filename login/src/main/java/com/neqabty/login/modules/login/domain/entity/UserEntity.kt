package com.neqabty.login.modules.login.domain.entity

data class UserEntity(
    val email: String? = "",
    val fullname: String = "",
    val id: Int = 0,
    val image: String? = "",
    val mobile: String = "",
    val nationalId: String = "",
    val password: String = ""
)
