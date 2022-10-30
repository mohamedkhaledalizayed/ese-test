package com.neqabty.shealth.auth.otp.domain.entity.sendotp



data class OTPEntity(
    val id: Int,
    val otp: Int,
    val phoneNumber: String
)