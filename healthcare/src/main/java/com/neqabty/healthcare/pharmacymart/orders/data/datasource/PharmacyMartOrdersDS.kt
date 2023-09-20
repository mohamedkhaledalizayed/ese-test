package com.neqabty.healthcare.pharmacymart.orders.data.datasource


import com.neqabty.healthcare.chefaa.orders.data.model.OrderRequestBody
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.pharmacymart.orders.data.model.*
import com.neqabty.healthcare.pharmacymart.orders.data.api.PharmacyMartOrdersApi
import com.neqabty.healthcare.pharmacymart.orders.data.model.addorder.AddOrderModel
import com.neqabty.healthcare.pharmacymart.orders.data.model.orderslist.OrdersListModel
import javax.inject.Inject

class PharmacyMartOrdersDS @Inject constructor(
    private val ordersApi: PharmacyMartOrdersApi,
    private val preferencesHelper: PreferencesHelper
) {

    suspend fun getOrderList(): OrdersListModel {
        return ordersApi.getOrdersList(OrderListRequestBody(userNumber = preferencesHelper.mobile))
    }

    suspend fun getOrder(orderRequestBody: OrderRequestBody): String {
        return ordersApi.getOrder(orderRequestBody = orderRequestBody)
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

}