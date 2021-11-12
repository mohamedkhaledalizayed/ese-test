package com.neqabty.yodawy.modules.order.data.repository


import com.neqabty.yodawy.modules.order.data.model.OrderListRequestBody
import com.neqabty.yodawy.modules.order.data.model.mapper.toOrderEntity
import com.neqabty.yodawy.modules.order.data.model.request.Item
import com.neqabty.yodawy.modules.order.data.model.request.PlaceOrderRequestBody
import com.neqabty.yodawy.modules.order.data.source.OrdersDS
import com.neqabty.yodawy.modules.order.domain.entity.OrderEntity
import com.neqabty.yodawy.modules.order.domain.entity.PlaceOrderParam
import com.neqabty.yodawy.modules.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun placeOrder(placeOrderParam: PlaceOrderParam): Flow<String> {
        return ordersDS.placeOrder(
            PlaceOrderRequestBody(
                placeOrderParam.addressId,
                placeOrderParam.items.map { Item(it.id,it.quantity) },
                placeOrderParam.mobile,
                placeOrderParam.notes,
                placeOrderParam.plan
            )
        ).map { it.id }
    }
}
