package com.neqabty.login.modules.login.domain.entity



data class AccountEntity(
    val email: String,
    val entity: Entity,
    val fullName: String?,
    val id: Int,
    val image: String?,
    val mobile: String,
    val nationalId: String
)