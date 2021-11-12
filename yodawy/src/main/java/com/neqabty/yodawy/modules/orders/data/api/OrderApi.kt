package com.neqabty.yodawy.modules.orders.data.api

import com.neqabty.yodawy.modules.address.data.model.AddressResponse
import com.neqabty.yodawy.modules.address.data.model.Response
import com.neqabty.yodawy.modules.orders.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.orders.data.model.OrderListResponse
import com.neqabty.yodawy.modules.orders.data.model.PlaceOrderResponse
import com.neqabty.yodawy.modules.orders.data.model.request.PlaceOrderRequestBody
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("order/list")
    suspend fun getOrdersList(@Body orderListRequestBody: OrderListRequestBody): Response<OrderListResponse>

    @POST("order/item")
    suspend fun placeOrder(@Body placeOrderRequestBody: PlaceOrderRequestBody): Response<PlaceOrderResponse>
}