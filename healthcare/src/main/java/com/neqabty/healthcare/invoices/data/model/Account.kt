package com.neqabty.healthcare.invoices.data.model


import androidx.annotation.Keep

@Keep
data class Account(
    val fullname: String?,
    val mobile: String?
)