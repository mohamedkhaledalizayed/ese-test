package com.neqabty.chefaa.modules.home.domain.repository

import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.domain.entities.ItemEntity
import com.neqabty.chefaa.modules.home.domain.entities.OrderEntity
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun registerUser(phoneNumber: String, userId: String, countryCode: String,nationalId:String, name:String): Flow<RegistrationEntity>
    fun getOrders(userNumber: String, pageNumber: Int, pageSize: Int): Flow<List<OrderEntity>>
    fun getOrderDetails(orderId: String): Flow<List<ItemEntity>>
}