package com.neqabty.chefaa.modules.orders.domain.repository

import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.domain.entities.PlaceOrderResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun placeOrder(items:List<OrderItemsEntity>,addressId:Int,phoneNumber:String,deliveryNote:String):Flow<PlaceOrderResult>
}