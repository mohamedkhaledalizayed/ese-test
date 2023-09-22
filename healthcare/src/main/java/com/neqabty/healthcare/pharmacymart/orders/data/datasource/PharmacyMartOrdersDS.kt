package com.neqabty.healthcare.pharmacymart.orders.data.datasource


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.pharmacymart.orders.data.model.*
import com.neqabty.healthcare.pharmacymart.orders.data.api.PharmacyMartOrdersApi
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.cancelorder.CancelOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails.OrderDetailsModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import javax.inject.Inject

class PharmacyMartOrdersDS @Inject constructor(
    private val ordersApi: PharmacyMartOrdersApi,
    private val preferencesHelper: PreferencesHelper
) {

    suspend fun getOrderList(): OrdersListModel {
        return ordersApi.getOrdersList(OrderListRequestBody(userNumber = preferencesHelper.mobile))
    }

    suspend fun getOrder(orderId: String): OrderDetailsModel {
        return ordersApi.getOrder(orderRequestBody = OrderRequestBody
            (userNumber = preferencesHelper.mobile,
            orderId = orderId))
    }

    suspend fun placeOrder(
        items: List<String>,
        addressId: Int,
        deliveryNote: String,
        deliveryMobile: String,
        orderByText: String,
        deviceInfo: String,
        currentLocation: String
    ): AddOrderModel {
        val body = AddOrderBody(
            addressId = addressId,
            deliverNote = deliveryNote,
            mobile = preferencesHelper.mobile,
            items = items,
            deliveryMobile = deliveryMobile,
            orderByText = orderByText,
            currentLocation = currentLocation,
            deviceInfo = deviceInfo
        )
        return ordersApi.placeOrder(body)
    }

    suspend fun cancelOrder(orderId: String, cancellationReason: String): CancelOrderModel {
        return ordersApi.cancelOrder(
            cancelOrderBody = CancelOrderBody(
                mobile = preferencesHelper.mobile,
                order_id = orderId,
                cancellation_reason = cancellationReason
            )
        )
    }

    suspend fun confirmOrder(orderId: String): String {
        return ordersApi.confirmOrder(
            confirmOrderBody = ConfirmOrderBody(
                mobile = preferencesHelper.mobile,
                order_id = orderId
            )
        )
    }

}