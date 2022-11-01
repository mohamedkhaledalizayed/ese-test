package com.neqabty.chefaa.modules.home.data.source

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.home.data.api.RegisterApi
import javax.inject.Inject

class RegisterDS @Inject constructor(private val registerApi: RegisterApi) {
    suspend fun registerUser(phoneNumber:String,userId:String,countryCode:String = "+20"): Boolean {
        val response =  registerApi.register(mobile = phoneNumber, userId = userId,countryCode=countryCode)
        return response.status
    }
}