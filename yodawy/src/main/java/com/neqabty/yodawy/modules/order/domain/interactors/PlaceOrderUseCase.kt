package com.neqabty.yodawy.modules.order.domain.interactors

import com.neqabty.yodawy.modules.order.domain.entity.PlaceOrderParam
import com.neqabty.yodawy.modules.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
     fun build(placeOrderParam: PlaceOrderParam): Flow<String> {
         return orderRepository.placeOrder(placeOrderParam = placeOrderParam)
     }
}