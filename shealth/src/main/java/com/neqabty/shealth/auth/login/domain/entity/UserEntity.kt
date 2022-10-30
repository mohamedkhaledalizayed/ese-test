package com.neqabty.shealth.auth.login.domain.entity



data class UserEntity(
    val token: String,
    val user: User
)