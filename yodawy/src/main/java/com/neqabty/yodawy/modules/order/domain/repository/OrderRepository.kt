package com.neqabty.yodawy.modules.order.domain.repository

import com.neqabty.yodawy.modules.order.domain.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrder(mobileNumber:String,pageNumber:Int,pageSize:Int): Flow<List<OrderEntity>>
}