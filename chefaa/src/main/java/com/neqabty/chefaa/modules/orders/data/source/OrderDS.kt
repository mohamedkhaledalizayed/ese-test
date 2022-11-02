package com.neqabty.chefaa.modules.orders.data.source

import com.neqabty.chefaa.modules.orders.data.api.OrdersApi
import com.neqabty.chefaa.modules.orders.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderDS @Inject constructor(private val ordersApi: OrdersApi) {
    suspend fun getOrderList(orderListRequestBody: OrderListRequestBody): List<OrderModel> {
        return ordersApi.getOrdersList(orderListRequestBody = orderListRequestBody).responseData!!.dataModels
    }

    suspend fun getOrder(orderRequestBody: OrderRequestBody): List<OrderItem> {
        return ordersApi.getOrder(orderRequestBody = orderRequestBody).responseData!!.dataModels
    }

    suspend fun placeOrder(placeOrderBody: PlaceOrderBody): PlaceOrderResponse {
        return ordersApi.placeOrder(placeOrderBody).responseData!!
    }
}