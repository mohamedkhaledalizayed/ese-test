package com.neqabty.healthcare.chefaa.orders.domain.usecases



import com.neqabty.healthcare.chefaa.orders.domain.entities.ItemEntity
import com.neqabty.healthcare.chefaa.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(orderId: String): Flow<List<ItemEntity>> {
        return orderRepository.getOrderDetails(orderId = orderId)
    }
}