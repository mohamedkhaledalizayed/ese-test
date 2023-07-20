package com.neqabty.healthcare.onboarding.contact.domain.entity


import androidx.annotation.Keep

@Keep
data class InstallmentsEntity(
    val tenor: String,
    val maxTenor: String,
    val instalmentValue: String
)