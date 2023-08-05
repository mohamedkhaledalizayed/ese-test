package com.neqabty.healthcare.chefaa.orders.data.repository



import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.home.data.model.DataModel
import com.neqabty.healthcare.chefaa.home.data.model.OrdersListModel
import com.neqabty.healthcare.chefaa.home.domain.entities.orders.DataEntity
import com.neqabty.healthcare.chefaa.home.domain.entities.orders.OrdersListEntity
import com.neqabty.healthcare.chefaa.orders.data.model.*
import com.neqabty.healthcare.chefaa.orders.data.source.OrderDS
import com.neqabty.healthcare.chefaa.orders.domain.entities.*
import com.neqabty.healthcare.chefaa.orders.domain.repository.OrderRepository
import com.neqabty.healthcare.core.data.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDS: OrderDS) : OrderRepository {
    override fun getOrders(
        userNumber: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<OrdersListEntity> {
        return flow {
            val orders = orderDS.getOrderList(OrderListRequestBody(userNumber))
            emit(
                orders.toOrdersListEntity()
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

    private fun OrdersListModel.toOrdersListEntity(): OrdersListEntity{
        return OrdersListEntity(
            message = message,
            status = status,
            status_code = status_code,
            data = data?.toDataEntity()
        )
    }

    private fun DataModel.toDataEntity(): DataEntity{
        return DataEntity(
            current_page = current_page,
            data = data.map { it.toOrderEntity() },
            first_page_url = first_page_url,
            from = from,
            next_page_url = next_page_url,
            path = path,
            per_page = per_page,
            prev_page_url = prev_page_url,
            to = to
        )
    }

    private fun com.neqabty.healthcare.chefaa.home.data.model.OrderModel.toOrderEntity(): OrderEntity {
        return OrderEntity(
            addressId = address_id,
            chefaaOrderNumber = chefaa_order_number,
            clientId = client_id,
            countryCode = country_code,
            createdAt = created_at,
            deliveryFees = delivery_fees ?: 0f,
            deliveryNote = delivery_note,
            id = id,
            orderStatus = order_status.toOrderStatusEntity(),
            phone = phone,
            price = price ?: 0f,
            priceBeforeDiscount = price_before_discount ?: 0f,
            status = status,
            updatedAt = updated_at,
            userPlan = user_plan,
            items = items.map { it.toItemEntity() }
        )
    }

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
                orderDS.placeOrder(getPlaceOrderBody(items, addressId, phoneNumber, deliveryNote, deviceInfo, currentLocation))
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

private fun OrderItem.toItemEntity(): ItemEntity {
    return ItemEntity(
        addressId = addressId,
        chefaaOrderNumber = chefaaOrderNumber,
        clientId = clientId,
        createdAt = createdAt,
        id = id,
        note = note ?: "",
        orderId = orderId,
        productId = productId,
        productImage = productImage ?: "",
        type = type,
        updatedAt = updatedAt,
        quantity = quantity,
        productName = productName ?: ""
    )
}

private fun com.neqabty.healthcare.chefaa.home.data.model.Item.toItemEntity(): ItemEntity {
    return ItemEntity(
        addressId = address_id,
        chefaaOrderNumber = chefaa_order_number,
        clientId = client_id,
        createdAt = created_at,
        id = id,
        note = note ?: "",
        orderId = order_id,
        productId = product_id,
        productImage = product_image ?: "",
        type = type,
        updatedAt = updated_at,
        quantity = quantity,
        productName = product_name ?: ""
    )
}

private fun com.neqabty.healthcare.chefaa.home.data.model.OrderStatus.toOrderStatusEntity(): OrderStatusEntity {
    return OrderStatusEntity(id, title_ar, title_en)
}

private fun ChefaaResponse<PlaceOrderResponse>.toPlaceOrderResult(): PlaceOrderResult {
    return PlaceOrderResult(
        status = this.status,
        statusCode = statusCode,
        message = this.messageAr
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
