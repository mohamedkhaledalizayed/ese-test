package com.neqabty.healthcare.onboarding.contact.domain.entity


import androidx.annotation.Keep

@Keep
data class InvoiceEntity(
    val tenor: String,
    val maxTenor: String,
    val instalmentValue: String
)