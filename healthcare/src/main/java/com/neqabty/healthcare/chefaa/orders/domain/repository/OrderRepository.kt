package com.neqabty.healthcare.chefaa.orders.domain.repository


import com.neqabty.healthcare.chefaa.orders.domain.entities.ItemEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.PlaceOrderResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(userNumber: String, pageNumber: Int, pageSize: Int): Flow<List<OrderEntity>>
    fun getOrderDetails(orderId: String): Flow<List<ItemEntity>>
    fun placeOrder(items:List<OrderItemsEntity>, addressId:Int, phoneNumber:String, deliveryNote:String, deviceInfo:String, currentLocation:String):Flow<PlaceOrderResult>
}