package com.neqabty.healthcare.pharmacymart.orders.data.model


import androidx.annotation.Keep

@Keep
data class ConfirmOrderBody(
    val mobile: String,
    val order_id: String
)