package com.neqabty.healthcare.profile.domain.entity



data class MinistryLicenseEntity(
    val id: Int,
    val license: String,
    val member: Int,
    val status: String?,
)