package com.neqabty.healthcare.pharmacymart.orders.data.api


import com.neqabty.healthcare.pharmacymart.orders.data.model.*
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.cancelorder.CancelOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.confirmorder.ConfirmOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.OrderDetailsModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import retrofit2.http.Body
import retrofit2.http.POST


interface PharmacyMartOrdersApi {

    @POST("healthcare/api/v1/pharmacy/orders")
    suspend fun getOrdersList(
        @Body orderListRequestBody: OrderListRequestBody
    ): OrdersListModel

    @POST("healthcare/api/v1/pharmacy/orders")
    suspend fun getOrder(@Body orderRequestBody: OrderRequestBody): OrderDetailsModel


    @POST("healthcare/api/v1/pharmacy/add-order")
    suspend fun placeOrder(@Body placeOrderBody: AddOrderBody): AddOrderModel

    @POST("healthcare/api/v1/pharmacy/cancel-order")
    suspend fun cancelOrder(@Body cancelOrderBody: CancelOrderBody): CancelOrderModel

    @POST("healthcare/api/v1/pharmacy/confirm-order")
    suspend fun confirmOrder(@Body confirmOrderBody: ConfirmOrderBody): ConfirmOrderModel

}