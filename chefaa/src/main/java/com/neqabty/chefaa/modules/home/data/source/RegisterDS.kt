package com.neqabty.chefaa.modules.home.data.source

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.home.data.api.RegisterApi
import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import javax.inject.Inject

class RegisterDS @Inject constructor(private val registerApi: RegisterApi) {
    suspend fun registerUser(phoneNumber:String,userId:String,countryCode:String = "+20",nationalId:String, name:String): RegistrationEntity {
        val response =  registerApi.register(mobile = phoneNumber, userId = userId,countryCode=countryCode, nationalId = nationalId, name = name)
        return RegistrationEntity(response.status, response.messageAr)
    }
}