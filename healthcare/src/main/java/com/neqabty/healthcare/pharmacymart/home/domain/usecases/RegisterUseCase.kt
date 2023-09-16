package com.neqabty.healthcare.pharmacymart.home.domain.usecases

import com.neqabty.healthcare.pharmacymart.home.domain.entity.RegistrationEntity
import com.neqabty.healthcare.pharmacymart.home.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    fun build(): Flow<RegistrationEntity> {
        return registerRepository.registerUser()
    }
}