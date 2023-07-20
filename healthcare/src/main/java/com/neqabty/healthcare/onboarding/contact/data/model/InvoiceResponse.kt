package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep

@Keep
data class InvoiceResponse(
    val tenor: String,
    val maxTenor: String,
    val instalmentValue: String
)