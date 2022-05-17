package com.neqabty.signup.modules.home.domain.entity

import com.google.gson.annotations.SerializedName

data class SignupParams(
    val entityCode: String = "",
    val membershipId: String = "",
    val mobile: String = "",
    val last4_national_id: String = "",
    val email: String = "",
)
