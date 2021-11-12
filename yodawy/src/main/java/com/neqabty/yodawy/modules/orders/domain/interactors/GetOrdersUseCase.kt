package com.neqabty.yodawy.modules.orders.domain.interactors

import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(mobileNumber:String,pageSize:Int,pageNumber:Int): Flow<List<OrderEntity>> {
        return orderRepository.getOrder(mobileNumber = mobileNumber,pageSize =pageSize ,pageNumber =pageNumber )
    }
}