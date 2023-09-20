package com.neqabty.healthcare.pharmacymart.orders.data.model.addorder


import androidx.annotation.Keep

@Keep
data class AddOrderModel(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val status_code: Int
)