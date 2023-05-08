package com.neqabty.healthcare.chefaa.home.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.orders.data.model.*
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST



interface RegisterApi {
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