package com.neqabty.yodawy.modules.orders.data.source

import com.neqabty.yodawy.modules.orders.data.api.OrderApi
import com.neqabty.yodawy.modules.orders.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.orders.data.model.OrderListResponse
import com.neqabty.yodawy.modules.orders.data.model.PlaceOrderResponse
import com.neqabty.yodawy.modules.orders.data.model.request.PlaceOrderRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun placePrescription(order: RequestBody, images: ArrayList<MultipartBody.Part>): Flow<PlaceOrderResponse>{
        return flow {
            emit(orderApi.placePrescription(order = order, images = images))
        }
    }
}