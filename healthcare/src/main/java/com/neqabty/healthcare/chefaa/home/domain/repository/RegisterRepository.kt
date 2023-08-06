package com.neqabty.healthcare.chefaa.home.domain.repository

import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    fun registerUser(phoneNumber: String, userId: String, countryCode: String,nationalId:String, name:String): Flow<RegistrationEntity>
}