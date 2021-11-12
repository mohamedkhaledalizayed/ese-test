package com.neqabty.yodawy.modules.order.domain.entity



data class PlaceOrderParam(
    val addressId: String,
    val items: List<Item>,
    val mobile: String,
    val notes: String,
    val plan: String
)