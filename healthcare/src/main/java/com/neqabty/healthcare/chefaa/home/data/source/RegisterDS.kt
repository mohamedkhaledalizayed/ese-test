package com.neqabty.healthcare.chefaa.home.data.source



import com.neqabty.healthcare.chefaa.home.data.api.RegisterApi
import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.orders.data.model.OrderItem
import com.neqabty.healthcare.chefaa.orders.data.model.OrderListRequestBody
import com.neqabty.healthcare.chefaa.orders.data.model.OrderModel
import com.neqabty.healthcare.chefaa.orders.data.model.OrderRequestBody
import javax.inject.Inject

class RegisterDS @Inject constructor(private val registerApi: RegisterApi) {

    suspend fun registerUser(phoneNumber:String,userId:String,countryCode:String = "+20",nationalId:String, name:String): RegistrationEntity {
        val response =  registerApi.register(mobile = phoneNumber, userId = userId,countryCode=countryCode, nationalId = nationalId, name = name)
        return RegistrationEntity(response.status, response.messageAr)
    }

}