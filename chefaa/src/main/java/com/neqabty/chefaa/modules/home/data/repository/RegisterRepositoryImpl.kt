package com.neqabty.chefaa.modules.home.data.repository

import com.neqabty.chefaa.modules.home.data.source.RegisterDS
import com.neqabty.chefaa.modules.home.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerDS: RegisterDS):RegisterRepository {
    override fun registerUser(
        phoneNumber: String,
        userId: String,
        countryCode: String
    ): Flow<Boolean> {
        return flow {
            emit(registerDS.registerUser(phoneNumber, userId, countryCode))
        }
    }
}