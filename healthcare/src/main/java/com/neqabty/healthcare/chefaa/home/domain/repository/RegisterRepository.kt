package com.neqabty.healthcare.chefaa.home.domain.repository

import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.ItemEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderEntity
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    fun registerUser(phoneNumber: String, userId: String, countryCode: String,nationalId:String, name:String): Flow<RegistrationEntity>
    fun getOrders(userNumber: String, pageNumber: Int, pageSize: Int): Flow<List<OrderEntity>>
    fun getOrderDetails(orderId: String): Flow<List<ItemEntity>>
}