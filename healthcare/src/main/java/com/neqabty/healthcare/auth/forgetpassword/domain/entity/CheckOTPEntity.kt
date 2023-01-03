package com.neqabty.healthcare.auth.forgetpassword.domain.entity



data class CheckOTPEntity(
    val message: String,
    val status: Boolean,
    val token: String
)