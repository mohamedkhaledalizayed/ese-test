package com.neqabty.healthcare.auth.forgetpassword.domain.entity



data class SendOTPEntity(
    val message: String,
    val status: Boolean
)