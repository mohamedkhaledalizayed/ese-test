package com.neqabty.login.modules.login.domain.entity

data class UserEntity(
    val name: String = "",
    val token: String = "",
    val email: String? = "",
    val fullname: String = "",
    val image: String? = "",
    val mobile: String = "",
    val nationalId: String = ""
)
