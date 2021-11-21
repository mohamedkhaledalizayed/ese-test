package com.neqabty.yodawy.modules.orders.domain.interactors

import com.neqabty.yodawy.modules.orders.domain.entity.PlaceOrderParam
import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
     fun build(placeOrderParam: PlaceOrderParam): Flow<String> {
         return orderRepository.placeOrder(placeOrderParam = placeOrderParam)
     }
}