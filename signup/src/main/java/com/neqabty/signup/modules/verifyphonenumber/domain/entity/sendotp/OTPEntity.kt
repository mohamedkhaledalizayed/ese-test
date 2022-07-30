package com.neqabty.signup.modules.verifyphonenumber.domain.entity.sendotp



data class OTPEntity(
    val id: Int,
    val otp: Int,
    val phoneNumber: String
)