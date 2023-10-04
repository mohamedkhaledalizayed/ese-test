package com.neqabty.healthcare.auth.signup.domain.entity


data class UserEntity(
    val email: String = "",
    val token: String = "",
    val fullname: String?,
    val id: Int = 0,
    val image: String?,
    val mobile: String = "",
    val nationalId: String? = "",
    val entityName: String = "",
    val entityImage: String? = "",
    val entityCode: String = ""
)