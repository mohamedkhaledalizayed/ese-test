package com.neqabty.healthcare.pharmacymart.orders.data.repository



import com.neqabty.healthcare.chefaa.orders.data.model.OrderRequestBody
import com.neqabty.healthcare.pharmacymart.orders.data.datasource.PharmacyMartOrdersDS
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrderStatusModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
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
            data = data.map { it.toOrderEntity() },
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

    override fun getOrderDetails(orderId: String): Flow<String> {
        return flow {
            emit(
                ordersDS.getOrder(OrderRequestBody(orderId))
            )
        }
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

    private fun AddOrderModel.toAddOrderEntity(): AddOrderEntity{
        return AddOrderEntity(
            status = status,
            statusCode = status_code,
            message = message
        )
    }
}