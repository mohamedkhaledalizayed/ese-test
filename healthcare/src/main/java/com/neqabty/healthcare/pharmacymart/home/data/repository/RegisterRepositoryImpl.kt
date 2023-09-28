package com.neqabty.healthcare.pharmacymart.home.data.repository

import com.neqabty.healthcare.pharmacymart.home.data.datasource.RegisterDS
import com.neqabty.healthcare.pharmacymart.home.data.model.RegisterModel
import com.neqabty.healthcare.pharmacymart.home.domain.entity.RegistrationEntity
import com.neqabty.healthcare.pharmacymart.home.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerDS: RegisterDS):RegisterRepository {

    override fun registerUser(): Flow<RegistrationEntity> {
        return flow {
            emit(registerDS.registerUser().toRegistrationEntity())
        }
    }

    private fun RegisterModel.toRegistrationEntity(): RegistrationEntity{
        return RegistrationEntity(
            status = status,
            msg = message
        )
    }

}