package com.neqabty.healthcare.auth.signup.domain.entity


data class SignupParams(
    val entityCode: String = "",
    val membershipId: String = "",
    val mobile: String = "",
    val national_id: String = "",
    val email: String = "",
)
