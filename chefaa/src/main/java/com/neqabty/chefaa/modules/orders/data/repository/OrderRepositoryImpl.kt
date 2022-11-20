package com.neqabty.chefaa.modules.orders.data.repository

import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.modules.orders.data.model.*
import com.neqabty.chefaa.modules.orders.data.source.OrderDS
import com.neqabty.chefaa.modules.orders.domain.entities.*
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDS: OrderDS) : OrderRepository {
    override fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<OrderEntity>> {
        return flow {
            val orders = orderDS.getOrderList(OrderListRequestBody(userNumber))
            emit(
                orders.map { it.toOrderEntity() }
            )
        }
    }

    override fun getOrderDetails(orderId: String): Flow<List<ItemEntity>> {
        return flow {
            emit(
                orderDS.getOrder(OrderRequestBody(orderId)).map { it.toItemEntity() }
            )
        }
    }

    private fun OrderModel.toOrderEntity(): OrderEntity {
        return OrderEntity(
            addressId = addressId,
            chefaaOrderNumber = chefaaOrderNumber,
            clientId = clientId,
            countryCode = countryCode,
            createdAt = createdAt,
            deliveryFees = deliveryFees ?: 0f,
            deliveryNote = deliveryNote,
            id = id,
            orderClient = orderClient.toOrderClientEntity(),
            orderStatus = orderStatus.toOrderStatusEntity(),
            phone = phone,
            price = price ?: 0f,
            priceBeforeDiscount = priceBeforeDiscount ?: 0f,
            status = status,
            updatedAt = updatedAt,
            userPlan = userPlan,
            items = items.map { it.toItemEntity() }
        )
    }

    override fun placeOrder(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String
    ): Flow<PlaceOrderResult> {
        return flow {
            emit(
                orderDS.placeOrder(getPlaceOrderBody(items, addressId, phoneNumber, deliveryNote))
                    .toPlaceOrderResult()
            )
        }
    }

    private fun getPlaceOrderBody(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String
    ): PlaceOrderBody {
        return PlaceOrderBody(
            addressId = addressId,
            phone = phoneNumber,
            deliverNote = deliveryNote,
            items = items.map { it.toOrderItemModel() })
    }
}

private fun OrderItem.toItemEntity(): ItemEntity {
    return ItemEntity(
        addressId = addressId,
        chefaaOrderNumber = chefaaOrderNumber,
        clientId = clientId,
        createdAt = createdAt,
        id = id,
        note = note ?: "",
        orderId = orderId,
        productId = productId ?: "",
        productImage = productImage ?: "",
        type = type,
        updatedAt = updatedAt,
        quantity = quantity,
        productName = productName ?: ""
    )
}

private fun OrderClient.toOrderClientEntity(): OrderClientEntity {
    return OrderClientEntity(id, name)
}

private fun OrderStatus.toOrderStatusEntity(): OrderStatusEntity {
    return OrderStatusEntity(id, titleAr, titleEn ?: "")
}

private fun PlaceOrderResponse.toPlaceOrderResult(): PlaceOrderResult {
    return PlaceOrderResult(
        addressId,
        chefaaOrderNumber,
        countryCode,
        createdAt,
        deliveryNote,
        id,
        phone,
        updatedAt,
        userId,
        userNumber,
        userPlan
    )
}

private fun OrderItemsEntity.toOrderItemModel(): OrderItemModel {
    return OrderItemModel(
        type = type.lowercase(),
        quantity = if (type == Constants.ITEMTYPES.PRODUCT.typeName) quantity else 1,
        image = if (type == Constants.ITEMTYPES.IMAGE.typeName) image else null,
        note = if (type == Constants.ITEMTYPES.NOTE.typeName) note else null,
        productId = if (type == Constants.ITEMTYPES.PRODUCT.typeName) productId else null
    )
}