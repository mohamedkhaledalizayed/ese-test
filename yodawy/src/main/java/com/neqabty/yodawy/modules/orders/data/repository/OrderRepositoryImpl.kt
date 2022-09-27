package com.neqabty.yodawy.modules.orders.data.repository

import com.neqabty.yodawy.modules.orders.domain.entity.PlaceOrderParam
import com.neqabty.yodawy.modules.orders.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.orders.data.model.mapper.toOrderEntity
import com.neqabty.yodawy.modules.orders.data.model.request.ItemRequest
import com.neqabty.yodawy.modules.orders.data.model.request.PlaceOrderRequestBody
import com.neqabty.yodawy.modules.orders.data.model.request.order.OrderRequestBody
import com.neqabty.yodawy.modules.orders.data.source.OrdersDS
import com.neqabty.yodawy.modules.orders.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val ordersDS: OrdersDS) : OrderRepository {
    override fun getOrder(
        mobileNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return ordersDS.getOrderList(
            OrderListRequestBody(
                mobile = mobileNumber,
                pageNumber = pageNumber,
                pageSize = pageSize
            )
        ).map { it.orders.map { it.toOrderEntity() } }
    }

    override fun getSpecificOrder(mobileNumber: String, orderId: String): Flow<OrderEntity> {
        return ordersDS.getOrder(
            OrderRequestBody(
                mobile = mobileNumber,
                orderId = orderId
            )
        ).map { it.toOrderEntity() }
    }

    override fun placeOrder(placeOrderParam: PlaceOrderParam): Flow<String> {
        return ordersDS.placeOrder(
            PlaceOrderRequestBody(
                placeOrderParam.addressId,
                placeOrderParam.itemParams.map { ItemRequest(it.id,it.quantity) },
                placeOrderParam.mobile,
                placeOrderParam.plan
            )
        ).map { it.id }
    }


    override fun placePrescription(order: RequestBody, images: ArrayList<MultipartBody.Part>): Flow<String> {
        return ordersDS.placePrescription(
            order,
            images
        ).map { it.id }
    }
}
