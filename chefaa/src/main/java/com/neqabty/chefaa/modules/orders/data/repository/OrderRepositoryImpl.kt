package com.neqabty.chefaa.modules.orders.data.repository

import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.orders.data.model.*
import com.neqabty.chefaa.modules.orders.data.source.OrderDS
import com.neqabty.chefaa.modules.orders.domain.entities.*
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDS: OrderDS) : OrderRepository {

    override fun placeOrder(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String,
        deviceInfo: String,
        currentLocation: String
    ): Flow<PlaceOrderResult> {
        return flow {
            emit(
                orderDS.placeOrder(
                    getPlaceOrderBody(
                        items,
                        addressId,
                        phoneNumber,
                        deliveryNote,
                        deviceInfo,
                        currentLocation
                    )
                )
                    .toPlaceOrderResult()
            )
        }
    }

    private fun getPlaceOrderBody(
        items: List<OrderItemsEntity>,
        addressId: Int,
        phoneNumber: String,
        deliveryNote: String,
        deviceInfo: String,
        currentLocation: String
    ): PlaceOrderBody {
        return PlaceOrderBody(
            addressId = addressId,
            phone = phoneNumber,
            deliverNote = deliveryNote,
            deviceInfo = deviceInfo,
            currentLocation = currentLocation,
            items = items.map { it.toOrderItemModel() })
    }
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

private fun ChefaaResponse<PlaceOrderResponse>.toPlaceOrderResult(): PlaceOrderResult {
    return PlaceOrderResult(
        status = this.status,
        statusCode = statusCode,
        message = this.messageAr
    )
}

