package com.neqabty.healthcare.pharmacymart.orders.domain.repository



import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails.OrderDetailsEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderslist.OrdersEntityList
import kotlinx.coroutines.flow.Flow


interface PharmacyMartOrdersRepository {
    fun getOrders(): Flow<OrdersEntityList>
    fun getOrderDetails(orderId: String): Flow<OrderDetailsEntity>
    fun placeOrder(items:List<String>, addressId:Int, deliveryNote:String, deliveryMobile:String, orderByText:String, deviceInfo:String, currentLocation:String):Flow<AddOrderEntity>
}