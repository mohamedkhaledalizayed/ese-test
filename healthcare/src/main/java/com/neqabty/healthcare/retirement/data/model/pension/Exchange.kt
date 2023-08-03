package com.neqabty.healthcare.retirement.data.model.pension


import androidx.annotation.Keep

@Keep
data class Exchange(
    val mony: Double,
    val sendbankdate: String
)