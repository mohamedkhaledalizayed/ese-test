package com.neqabty.chefaa.modules.orders.domain.usecases

import com.neqabty.chefaa.modules.orders.domain.entities.ItemEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderClientEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(orderId: String): Flow<List<ItemEntity>> {
        return orderRepository.getOrderDetails(orderId = orderId)
    }
}