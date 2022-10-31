package com.neqabty.chefaa.modules.orders.domain.repository

import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.domain.entities.PlaceOrderResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>>

    fun getOrderDetails(
        orderId: String
    ): Flow<OrderEntity>
    fun placeOrder(items:List<OrderItemsEntity>,addressId:Int,phoneNumber:String,deliveryNote:String):Flow<PlaceOrderResult>
}