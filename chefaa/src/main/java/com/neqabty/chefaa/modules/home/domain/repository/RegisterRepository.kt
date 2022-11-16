package com.neqabty.chefaa.modules.home.domain.repository

import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    fun registerUser(phoneNumber: String, userId: String, countryCode: String,nationalId:String, name:String): Flow<RegistrationEntity>
}