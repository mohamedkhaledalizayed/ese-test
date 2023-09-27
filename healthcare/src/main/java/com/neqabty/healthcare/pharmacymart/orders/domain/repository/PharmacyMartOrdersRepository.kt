package com.neqabty.healthcare.pharmacymart.orders.domain.repository



import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.cancelorder.CancelOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.confirmorder.ConfirmOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import kotlinx.coroutines.flow.Flow


interface PharmacyMartOrdersRepository {
    fun getOrders(): Flow<OrdersEntityList>
    fun getOrderDetails(orderId: String): Flow<OrderDetailsEntity>
    fun placeOrder(items:List<String>, addressId:Int, deliveryNote:String, deliveryMobile:String, orderByText:String, deviceInfo:String, currentLocation:String):Flow<AddOrderEntity>
    fun cancelOrder(orderId: String, cancellationReason: String): Flow<CancelOrderEntity>
    fun confirmOrder(orderId: String): Flow<ConfirmOrderEntity>
}