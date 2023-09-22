package com.neqabty.healthcare.pharmacymart.orders.data.model


import androidx.annotation.Keep

@Keep
data class CancelOrderBody(
    val cancellation_reason: String,
    val mobile: String,
    val order_id: String
)