package com.neqabty.signup.modules.home.domain.entity


data class UserEntity(
    val email: String = "",
    val fullname: String = "",
    val id: Int = 0,
    val image: String = "",
    val mobile: String = "",
    val nationalId: String = ""
)
