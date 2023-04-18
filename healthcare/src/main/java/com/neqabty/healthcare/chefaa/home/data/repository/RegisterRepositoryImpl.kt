package com.neqabty.healthcare.chefaa.home.data.repository

import com.neqabty.healthcare.chefaa.home.data.source.RegisterDS
import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.home.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerDS: RegisterDS):RegisterRepository {
    override fun registerUser(
        phoneNumber: String,
        userId: String,
        countryCode: String,
        nationalId:String,
        name:String
    ): Flow<RegistrationEntity> {
        return flow {
            emit(registerDS.registerUser(phoneNumber, userId, countryCode, nationalId, name))
        }
    }
}