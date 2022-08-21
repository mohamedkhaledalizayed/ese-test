package com.neqabty.signup.modules.signup.domain.entity


data class SignupParams(
    val entityCode: String = "",
    val membershipId: String = "",
    val mobile: String = "",
    val national_id: String = "",
    val email: String = "",
)
