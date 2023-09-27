package com.neqabty.healthcare.pharmacymart.orders.data.model.confirmorder


import androidx.annotation.Keep

@Keep
data class ConfirmOrderModel(
    val data: String?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)