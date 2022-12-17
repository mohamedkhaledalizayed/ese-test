package com.neqabty.chefaa.modules.orders.domain.entities


data class PlaceOrderResult(
    val status: Boolean = false,
    val statusCode: Int = 0,
    val message: String = ""
)