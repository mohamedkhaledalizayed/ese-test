package com.neqabty.chefaa.modules.orders.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.orders.data.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface OrdersApi {
    @POST("add-order")
    suspend fun placeOrder(@Body placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse>
}