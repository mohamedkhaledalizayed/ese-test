package com.neqabty.healthcare.chefaa.orders.domain.usecases



import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.PlaceOrderResult
import com.neqabty.healthcare.chefaa.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    fun build(items:List<OrderItemsEntity>, addressId:Int, phoneNumber:String, deliveryNote:String, deviceInfo:String, currentLocation:String): Flow<PlaceOrderResult> {
        return orderRepository.placeOrder(items, addressId, phoneNumber, deliveryNote, deviceInfo, currentLocation)
    }
}