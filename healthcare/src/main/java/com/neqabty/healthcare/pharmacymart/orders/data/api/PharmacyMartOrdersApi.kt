package com.neqabty.healthcare.pharmacymart.orders.data.api


import com.neqabty.healthcare.pharmacymart.orders.data.model.AddOrderBody
import com.neqabty.healthcare.pharmacymart.orders.data.model.OrderListRequestBody
import com.neqabty.healthcare.pharmacymart.orders.data.model.OrderRequestBody
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.OrderDetailsModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import retrofit2.http.Body
import retrofit2.http.POST


interface PharmacyMartOrdersApi {

    @POST("pharmacy/orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): OrdersListModel

    @POST("pharmacy/orders")
    suspend fun getOrder(@Body orderRequestBody: OrderRequestBody): OrderDetailsModel

    @POST("pharmacy/add-order")
    suspend fun placeOrder(@Body placeOrderBody: AddOrderBody): AddOrderModel

}