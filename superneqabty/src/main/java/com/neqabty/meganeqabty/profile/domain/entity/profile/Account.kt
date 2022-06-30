package com.neqabty.meganeqabty.profile.domain.entity.profile



data class Account(
    val email: String,
    val fullname: String,
    val id: Int,
    val image: String?,
    val mobile: String,
    val nationalId: String,
)