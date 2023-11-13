package com.neqabty.healthcare.chefaa.orders.data.source

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.home.data.model.OrdersListModel
import com.neqabty.healthcare.chefaa.orders.data.api.OrdersApi
import com.neqabty.healthcare.chefaa.orders.data.model.*
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class OrderDS @Inject constructor(private val ordersApi: OrdersApi, private val preferencesHelper: PreferencesHelper) {
    suspend fun getOrderList(orderListRequestBody: OrderListRequestBody): OrdersListModel {
        return ordersApi.getOrdersList(token = preferencesHelper.token, orderListRequestBody = orderListRequestBody)
    }

    suspend fun getOrder(orderRequestBody: OrderRequestBody): List<OrderItem> {
        return ordersApi.getOrder(token = preferencesHelper.token, orderRequestBody = orderRequestBody).responseData!!.dataModels
    }

    suspend fun placeOrder(placeOrderBody: PlaceOrderBody): ChefaaResponse<PlaceOrderResponse> {
        return ordersApi.placeOrder(token = preferencesHelper.token, placeOrderBody)
    }
}