package com.neqabty.yodawy.modules.order.data.source

import com.neqabty.yodawy.modules.order.data.api.OrderApi
import com.neqabty.yodawy.modules.order.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.order.data.model.OrderListResponse
import com.neqabty.yodawy.modules.order.data.model.PlaceOrderResponse
import com.neqabty.yodawy.modules.order.data.model.request.PlaceOrderRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrdersDS @Inject constructor(private val orderApi: OrderApi) {
    fun getOrderList(orderListRequestBody: OrderListRequestBody): Flow<OrderListResponse> {
        return flow {
            emit(orderApi.getOrdersList(orderListRequestBody).dataModel)
        }
    }

    fun placeOrder(placeOrderRequestBody: PlaceOrderRequestBody): Flow<PlaceOrderResponse>{
        return flow {
            emit(orderApi.placeOrder(placeOrderRequestBody).dataModel)
        }
    }
}