package com.neqabty.healthcare.pharmacymart.orders.domain.repository



import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.PlaceOrderResult
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import kotlinx.coroutines.flow.Flow


interface PharmacyMartOrdersRepository {
    fun getOrders(): Flow<OrdersEntityList>
    fun getOrderDetails(orderId: String): Flow<String>
    fun placeOrder(items:List<OrderItemsEntity>, addressId:Int, deliveryNote:String, deviceInfo:String, currentLocation:String):Flow<PlaceOrderResult>
}