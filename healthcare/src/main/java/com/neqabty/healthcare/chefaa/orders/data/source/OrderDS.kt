package com.neqabty.healthcare.chefaa.orders.data.source

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.orders.data.api.OrdersApi
import com.neqabty.healthcare.chefaa.orders.data.model.*
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