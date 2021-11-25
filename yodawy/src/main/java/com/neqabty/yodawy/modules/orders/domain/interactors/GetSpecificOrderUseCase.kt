package com.neqabty.yodawy.modules.orders.domain.interactors

import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(mobileNumber:String, orderId: String): Flow<OrderEntity> {
        return orderRepository.getSpecificOrder(mobileNumber = mobileNumber, orderId = orderId )
    }
}