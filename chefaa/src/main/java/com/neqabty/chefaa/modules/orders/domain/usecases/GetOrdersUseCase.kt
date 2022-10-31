package com.neqabty.chefaa.modules.orders.domain.usecases

import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(userNumber:String,pageSize:Int,pageNumber:Int): Flow<List<OrderEntity>> {
        return orderRepository.getOrders(userNumber = userNumber,pageSize =pageSize ,pageNumber =pageNumber )
    }
}