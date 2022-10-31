package com.neqabty.chefaa.modules.orders.data.repository

import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.modules.orders.data.model.*
import com.neqabty.chefaa.modules.orders.data.source.OrderDS
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.domain.entities.PlaceOrderResult
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDS: OrderDS):OrderRepository {
    override fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return flow {
            emit(orderDS.getOrderList(OrderListRequestBody(userNumber)).toOrderModelResult())
        }
    }

    override fun getOrderDetails(orderId: String): Flow<OrderEntity> {
        TODO("Not yet implemented")
    }

    private fun OrderListResponse.toOrderModelResult(): List<OrderEntity> {
        return orders.map { OrderEntity(id = it.id, addressId = it.addressId, deliveryNote = it.deliveryNote, creationDate = it.creationDate, status = it.status) }
    }

    override fun placeOrder(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String
    ): Flow<PlaceOrderResult> {
        return flow {
            emit(orderDS.placeOrder(getPlaceOrderBody(items,addressId,phoneNumber,deliveryNote)).toPlaceOrderResult())
        }
    }

    private fun getPlaceOrderBody(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String
    ): PlaceOrderBody {
        return PlaceOrderBody(addressId = addressId,phone = phoneNumber, deliverNote = deliveryNote, items = items.map { it.toOrderItemModel() })
    }
}

private fun PlaceOrderResponse.toPlaceOrderResult(): PlaceOrderResult {
    return PlaceOrderResult(addressId, chefaaOrderNumber, countryCode, createdAt, deliveryNote, id, phone, updatedAt, userId, userNumber, userPlan)
}

private fun OrderItemsEntity.toOrderItemModel(): OrderItemModel {
    return OrderItemModel(
        type = type.lowercase(),
        quantity = if (type == Constants.ITEMTYPES.PRODUCT.typeName) quantity else 1,
        image = if(type == Constants.ITEMTYPES.IMAGE.typeName) image else null,
        note = if(type == Constants.ITEMTYPES.NOTE.typeName) note else null,
        productId = if(type == Constants.ITEMTYPES.PRODUCT.typeName) productId else null
    )
}
