package com.neqabty.healthcare.commen.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InvoiceResponse(
    val tenor: String,
    val maxTenor: String,
    val instalmentValue: String
)