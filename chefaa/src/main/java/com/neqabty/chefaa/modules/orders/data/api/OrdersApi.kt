package com.neqabty.chefaa.modules.orders.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.orders.data.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface OrdersApi {
    @POST("get-orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): ChefaaResponse<OrderListResponse>

    @POST("get-order-items")
    suspend fun getOrder(
        @Body orderRequestBody: OrderRequestBody): ChefaaResponse<OrderModel>

    @POST("add-order")
    suspend fun placeOrder(@Body placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse>
}