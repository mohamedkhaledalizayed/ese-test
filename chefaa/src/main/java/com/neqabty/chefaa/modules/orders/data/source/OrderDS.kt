package com.neqabty.chefaa.modules.orders.data.source

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.orders.data.api.OrdersApi
import com.neqabty.chefaa.modules.orders.data.model.*
import javax.inject.Inject

class OrderDS @Inject constructor(private val ordersApi: OrdersApi) {
    suspend fun getOrderList(orderListRequestBody: OrderListRequestBody): List<OrderModel> {
        return ordersApi.getOrdersList(orderListRequestBody = orderListRequestBody).responseData!!.dataModels
    }

    suspend fun getOrder(orderRequestBody: OrderRequestBody): List<OrderItem> {
        return ordersApi.getOrder(orderRequestBody = orderRequestBody).responseData!!.dataModels
    }

    suspend fun placeOrder(placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse> {
        return ordersApi.placeOrder(placeOrderBody)
    }
}