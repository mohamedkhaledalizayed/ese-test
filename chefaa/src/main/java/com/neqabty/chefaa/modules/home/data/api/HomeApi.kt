package com.neqabty.chefaa.modules.home.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.home.data.model.OrderItem
import com.neqabty.chefaa.modules.home.data.model.OrderListRequestBody
import com.neqabty.chefaa.modules.home.data.model.OrderModel
import com.neqabty.chefaa.modules.home.data.model.OrderRequestBody
import com.neqabty.chefaa.modules.orders.data.model.*
import retrofit2.http.*

interface HomeApi {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("mobile") mobile: String,
        @Field("user_number") userId: String,
        @Field("country_code") countryCode: String,
        @Field("national_id") nationalId: String,
        @Field("name") name: String
    ): ChefaaResponse<Unit>

    @POST("get-orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): ChefaaResponse<OrdersWrapperModel<List<OrderModel>>>

    @POST("get-order-items")
    suspend fun getOrder(@Body orderRequestBody: OrderRequestBody): ChefaaResponse<OrdersWrapperModel<List<OrderItem>>>



}