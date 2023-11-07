package com.neqabty.healthcare.pharmacymart.orders.data.repository



import com.neqabty.healthcare.pharmacymart.orders.data.datasource.PharmacyMartOrdersDS
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.cancelorder.CancelOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.confirmorder.ConfirmOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.AddressModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.ItemsModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.OrderDetailsModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.OrderItemModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrderStatusModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.cancelorder.CancelOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.confirmorder.ConfirmOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.AddressEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.ItemsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderItemEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrderStatusEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PharmacyMartOrdersRepositoryImpl @Inject constructor(private val ordersDS: PharmacyMartOrdersDS):
    PharmacyMartOrdersRepository {

    override fun getOrders(): Flow<OrdersEntityList> {
        return flow {
            val orders = ordersDS.getOrderList()
            emit(orders.toOrdersEntityList())
        }
    }

    private fun OrdersListModel.toOrdersEntityList(): OrdersEntityList{
        return OrdersEntityList(
            data = data?.data?.map { it.toOrderEntity()  },
            message = message,
            status = status,
            status_code = status_code
        )
    }

    private fun OrderModel.toOrderEntity(): OrderEntity{
        return OrderEntity(
            status = status,
            id = id,
            mobile = mobile,
            paid = paid,
            order_number = order_number,
            delivery_fees = delivery_fees,
            cancelled_by = cancelled_by ?: "",
            order_text = order_text ?: "",
            price_after_discount = price_after_discount ?: "",
            imported_discount_percentage = imported_discount_percentage ?: "",
            order_status = order_status.toOrderStatusEntity(),
            pharmacy_user_id = pharmacy_user_id,
            actual_delivery_datetime = actual_delivery_datetime ?: "",
            created_at = created_at,
            local_discount_percentage = local_discount_percentage ?: "",
            price_before_discount = price_before_discount ?: "",
            total_price = total_price ?: ""
        )
    }

    private fun OrderStatusModel.toOrderStatusEntity(): OrderStatusEntity{
        return OrderStatusEntity(
            created_at = created_at ?: "",
            id = id,
            title_ar = title_ar ?: "",
            title_en = title_en ?: ""
        )
    }

    override fun getOrderDetails(orderId: String): Flow<OrderDetailsEntity> {
        return flow {
            emit(
                ordersDS.getOrder(orderId).toOrderDetailsEntity()
            )
        }
    }

    private fun OrderDetailsModel.toOrderDetailsEntity(): OrderDetailsEntity{
        return OrderDetailsEntity(
            data = data.data.map { it.toOrderItemEntity() },
            message = message,
            status = status,
            statusCode = status_code
        )
    }

    private fun OrderItemModel.toOrderItemEntity(): OrderItemEntity {
        return OrderItemEntity(
            actualDeliveryDatetime = actual_delivery_datetime ?: "",
            attachments = attachments,
            mobile = mobile,
            status = status,
            items = items.map { it.toItemsEntity() },
            orderStatusId = order_status.id,
            orderStatusTitle = order_status.title_ar,
            address = addresses.toAddressEntity(),
            id = id,
            priceBeforeDiscount = price_before_discount ?: "",
            priceAfterDiscount = price_after_discount ?: "",
            cancellationReason = cancellation_reason ?: "",
            totalPrice = total_price ?: "",
            paid = paid,
            deliveryFees = delivery_fees ?: "",
            orderNumber = order_number,
            deliveryMobile = delivery_mobile ?: "",
            createdAt = created_at ?: "",
            deliveryNote = delivery_note ?: "",
            cancelledBy = cancelled_by ?: "",
            orderText = order_text ?: "",
            importedDiscountPercentage = imported_discount_percentage ?: "",
            localDiscountPercentage = local_discount_percentage ?: ""
        )
    }

    private fun ItemsModel.toItemsEntity(): ItemsEntity{
        return ItemsEntity(
            name = name,
            price = "$price",
            priceBeforeDiscount = price_before_discount,
            orderId = order_id,
            id = id,
            discountPercentage = discount_percentage,
            imported = imported,
            productImage = product_image ?: "",
            quantity = quantity
        )
    }

    private fun AddressModel.toAddressEntity(): AddressEntity {
        return AddressEntity(
            apartment = apartment,
            landLark = land_mark,
            id = id,
            streetName = street_name,
            title = title,
            buildingNo = building_no,
            floor = floor,
            lat = lat,
            long = long
        )
    }


    override fun placeOrder(
        items: List<String>,
        addressId: Int,
        deliveryNote: String,
        deliveryMobile: String,
        orderByText: String,
        deviceInfo: String,
        currentLocation: String
    ): Flow<AddOrderEntity> {
        return flow {
            emit(ordersDS.placeOrder(
                items = items,
                addressId = addressId,
                deliveryNote = deliveryNote,
                deliveryMobile = deliveryMobile,
                orderByText = orderByText,
                deviceInfo = deviceInfo,
                currentLocation = currentLocation
            ).toAddOrderEntity())
        }
    }

    override fun cancelOrder(orderId: String, cancellationReason: String): Flow<CancelOrderEntity> {
        return flow {
            emit(ordersDS.cancelOrder(orderId, cancellationReason).toCancelOrderEntity())
        }
    }

    private fun CancelOrderModel.toCancelOrderEntity(): CancelOrderEntity{
        return CancelOrderEntity(
            data = data,
            message = message,
            status = status,
            statusCode = status_code
        )
    }

    private fun AddOrderModel.toAddOrderEntity(): AddOrderEntity{
        return AddOrderEntity(
            status = status,
            statusCode = status_code,
            message = message
        )
    }

    override fun confirmOrder(orderId: String): Flow<ConfirmOrderEntity> {
        return flow {
            emit(ordersDS.confirmOrder(orderId).toConfirmOrderEntity())
        }
    }

    private fun ConfirmOrderModel.toConfirmOrderEntity(): ConfirmOrderEntity{
        return ConfirmOrderEntity(
            data = data ?: "",
            message = message,
            status = status,
            status_code = status_code
        )
    }

}