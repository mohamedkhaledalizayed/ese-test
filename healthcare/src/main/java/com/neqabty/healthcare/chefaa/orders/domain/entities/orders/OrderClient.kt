package com.neqabty.healthcare.chefaa.orders.domain.entities.orders


import androidx.annotation.Keep

@Keep
data class OrderClient(
    val id: String,
    val name: String
)