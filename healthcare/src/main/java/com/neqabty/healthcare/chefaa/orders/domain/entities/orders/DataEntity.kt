package com.neqabty.healthcare.chefaa.orders.domain.entities.orders


import androidx.annotation.Keep
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity

@Keep
data class DataEntity(
    val data: List<OrderEntity>,
)