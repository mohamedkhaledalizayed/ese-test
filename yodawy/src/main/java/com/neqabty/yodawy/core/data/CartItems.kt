package com.neqabty.yodawy.core.data

import com.neqabty.yodawy.modules.orders.data.model.request.ItemRequest

data class CartItems(
    val id: String,
    val name: String,
    val details: String,
    var price: Float,
    val image: String,
    var quantity: Int
    )