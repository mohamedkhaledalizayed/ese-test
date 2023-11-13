package com.neqabty.healthcare.chefaa.orders.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.home.data.model.OrdersListModel
import com.neqabty.healthcare.chefaa.orders.data.model.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OrdersApi {
    @POST("healthcare/api/v1/chefaa/get-orders")
    suspend fun getOrdersList(
        @Header("Authorization") token: String,
        @Body orderListRequestBody: OrderListRequestBody
    ): OrdersListModel

    @POST("healthcare/api/v1/chefaa/get-order-items")
    suspend fun getOrder(@Header("Authorization") token: String, @Body orderRequestBody: OrderRequestBody): ChefaaResponse<OrdersWrapperModel<List<OrderItem>>>

    @POST("healthcare/api/v1/chefaa/add-order")
    suspend fun placeOrder(@Header("Authorization") token: String, @Body placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse>
}