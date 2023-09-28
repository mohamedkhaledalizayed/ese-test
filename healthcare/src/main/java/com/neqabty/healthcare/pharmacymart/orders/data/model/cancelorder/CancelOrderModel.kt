package com.neqabty.healthcare.pharmacymart.orders.data.model.cancelorder


import androidx.annotation.Keep

@Keep
data class CancelOrderModel(
    val data: String,
    val message: String,
    val status: Boolean,
    val status_code: Int
)