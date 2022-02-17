package com.neqabty.signup.modules.home.domain.entity

import com.google.gson.annotations.SerializedName

data class SignupParams(
    val entityCode: String = "",
    val licenceNumber: String = "",
    val membershipId: String = "",
    val mobile: String = "",
    val nationalId: String = "",
    val password: String = ""
)
