package com.neqabty.chefaa.modules.home.data.source

import com.neqabty.chefaa.modules.home.data.api.HomeApi
import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.data.model.OrderItem
import com.neqabty.chefaa.modules.home.data.model.OrderListRequestBody
import com.neqabty.chefaa.modules.home.data.model.OrderModel
import com.neqabty.chefaa.modules.home.data.model.OrderRequestBody
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {
    suspend fun registerUser(phoneNumber:String,userId:String,countryCode:String = "+20",nationalId:String, name:String): RegistrationEntity {
        val response =  homeApi.register(mobile = phoneNumber, userId = userId,countryCode=countryCode, nationalId = nationalId, name = name)
        return RegistrationEntity(response.status, response.messageAr)
    }

    suspend fun getOrderList(orderListRequestBody: OrderListRequestBody): List<OrderModel> {
        return homeApi.getOrdersList(orderListRequestBody = orderListRequestBody).responseData!!.dataModels
    }

    suspend fun getOrder(orderRequestBody: OrderRequestBody): List<OrderItem> {
        return homeApi.getOrder(orderRequestBody = orderRequestBody).responseData!!.dataModels
    }
}