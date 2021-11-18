package com.neqabty.yodawy.modules.orders.domain.entity



data class PlaceOrderParam(
    val addressId: String,
    val itemParams: List<ItemParam>,
    val mobile: String,
    val notes: String,
    val plan: String
)