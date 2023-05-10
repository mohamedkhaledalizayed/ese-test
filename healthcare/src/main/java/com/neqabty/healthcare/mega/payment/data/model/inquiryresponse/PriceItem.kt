package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep

@Keep
data class PriceItem(
    val fees: String,
    val gateway: String,
    val price: Double
)