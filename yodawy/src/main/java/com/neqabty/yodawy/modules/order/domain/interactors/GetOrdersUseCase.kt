package com.neqabty.yodawy.modules.order.domain.interactors

import com.neqabty.yodawy.modules.order.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend fun build(mobileNumber:String,pageSize:Int,pageNumber:Int): Flow<List<OrderEntity>> {
        return orderRepository.getOrder(mobileNumber = mobileNumber,pageSize =pageSize ,pageNumber =pageNumber )
    }
}