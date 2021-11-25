package com.neqabty.yodawy.modules.orders.data.api

import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.FIXED_TOKEN
import com.neqabty.yodawy.core.data.Constants.YODAWY_URL
import com.neqabty.yodawy.core.data.Constants.selectedAddress
import com.neqabty.yodawy.modules.address.data.model.Response
import com.neqabty.yodawy.modules.orders.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.orders.data.model.OrderListResponse
import com.neqabty.yodawy.modules.orders.data.model.OrderModel
import com.neqabty.yodawy.modules.orders.data.model.PlaceOrderResponse
import com.neqabty.yodawy.modules.orders.data.model.request.PlaceOrderRequestBody
import com.neqabty.yodawy.modules.orders.data.model.request.order.OrderRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@JvmSuppressWildcards
interface OrderApi {
    @POST("order/list")
    suspend fun getOrdersList(
        @Header("Authorization") jwt: String = "Bearer " + Constants.jwt,
        @Header("Accept") Accept: String = "application/json",
        @Body orderListRequestBody: OrderListRequestBody): Response<OrderListResponse>

    @POST("order/show")
    suspend fun getOrder(
        @Header("Authorization") jwt: String = "Bearer " + Constants.jwt,
        @Header("Accept") Accept: String = "application/json",
        @Body orderRequestBody: OrderRequestBody): Response<OrderModel>

    @POST("order/item")
    suspend fun placeOrder(
        @Header("Authorization") jwt: String = "Bearer " + Constants.jwt,
        @Header("Accept") Accept: String = "application/json",
        @Body placeOrderRequestBody: PlaceOrderRequestBody): Response<PlaceOrderResponse>

    @Multipart
    @POST
    @Headers("No-Authentication: true")
    suspend fun placePrescription(
        @Header("X-Yodawy-Signature") signature: String = selectedAddress.signature,
        @Header("Authorization") token: String = FIXED_TOKEN,
        @Url url: String = YODAWY_URL,
        @Part("order") order: RequestBody,
        @Part images: ArrayList<MultipartBody.Part>
    ): PlaceOrderResponse
}