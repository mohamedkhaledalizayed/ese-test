package com.neqabty.healthcare.pharmacymart.orders.data.api


import com.neqabty.healthcare.chefaa.orders.data.model.OrderRequestBody
import com.neqabty.healthcare.chefaa.orders.data.model.PlaceOrderBody
import com.neqabty.healthcare.pharmacymart.orders.data.model.OrderListRequestBody
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import retrofit2.http.Body
import retrofit2.http.POST


interface PharmacyMartOrdersApi {

    @POST("pharmacy/orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): OrdersListModel

    @POST("get-order-items")
    suspend fun getOrder(@Body orderRequestBody: OrderRequestBody): String

    @POST("add-order")
    suspend fun placeOrder(@Body placeOrderBody: PlaceOrderBody): String

}