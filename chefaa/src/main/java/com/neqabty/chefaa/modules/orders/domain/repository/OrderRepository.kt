package com.neqabty.chefaa.modules.orders.domain.repository

import com.neqabty.chefaa.modules.orders.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(userNumber: String, pageNumber: Int, pageSize: Int): Flow<List<OrderEntity>>
    fun getOrderDetails(orderId: String): Flow<List<ItemEntity>>
    fun placeOrder(items:List<OrderItemsEntity>, addressId:Int, phoneNumber:String, deliveryNote:String, deviceInfo:String, currentLocation:String):Flow<PlaceOrderResult>
}