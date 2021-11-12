package com.neqabty.yodawy.modules.orders.domain.repository

import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.entity.PlaceOrderParam
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrder(
        mobileNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>>

    fun placeOrder(placeOrderParam: PlaceOrderParam): Flow<String>
}