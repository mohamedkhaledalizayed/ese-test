package com.neqabty.login.modules.login.presentation.model


data class UserUIModel(
    val email: String? = "",
    val fullname: String = "",
    val id: Int = 0,
    val image: String? = "",
    val mobile: String = "",
    val nationalId: String = "",
    val password: String = ""
)
