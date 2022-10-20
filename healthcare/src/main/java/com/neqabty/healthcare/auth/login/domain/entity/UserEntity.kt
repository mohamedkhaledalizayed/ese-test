package com.neqabty.healthcare.auth.login.domain.entity



data class UserEntity(
    val token: String,
    val user: User
)