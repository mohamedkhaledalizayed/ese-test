package com.neqabty.chefaa.modules.orders.domain.usecases

import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.domain.entities.PlaceOrderResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(items:List<OrderItemsEntity>, addressId:Int, phoneNumber:String, deliveryNote:String): Flow<PlaceOrderResult> {
        return orderRepository.placeOrder(items, addressId, phoneNumber, deliveryNote)
    }
}