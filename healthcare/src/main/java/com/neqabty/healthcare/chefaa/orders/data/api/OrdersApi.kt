package com.neqabty.healthcare.chefaa.orders.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.orders.data.model.*
import com.neqabty.healthcare.chefaa.orders.data.model.orders.ChefaaOrdersModel
import retrofit2.http.Body
import retrofit2.http.POST

interface OrdersApi {
    @POST("get-orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): ChefaaOrdersModel

    @POST("get-order-items")
    suspend fun getOrder(@Body orderRequestBody: OrderRequestBody): ChefaaResponse<OrdersWrapperModel<List<OrderItem>>>

    @POST("add-order")
    suspend fun placeOrder(@Body placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse>
}