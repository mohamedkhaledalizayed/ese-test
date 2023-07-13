package com.neqabty.healthcare.chefaa.orders.domain.entities.orders


import androidx.annotation.Keep

@Keep
data class OrderStatus(
    val id: Int,
    val title_ar: String,
    val title_en: String
)