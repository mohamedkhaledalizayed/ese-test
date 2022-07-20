package com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp



data class OTPEntity(
    val id: Int,
    val otp: Int,
    val phoneNumber: String
)