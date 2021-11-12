package com.neqabty.yodawy.modules.order.data.api

import com.neqabty.yodawy.modules.address.data.model.Response
import com.neqabty.yodawy.modules.order.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.order.data.model.OrderListResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("order/list")
    suspend fun getOrdersList(@Body orderListRequestBody: OrderListRequestBody): Response<OrderListResponse>
}