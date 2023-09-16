package com.neqabty.healthcare.pharmacymart.home.domain.repository

import com.neqabty.healthcare.pharmacymart.home.domain.entity.RegistrationEntity
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    fun registerUser(): Flow<RegistrationEntity>
}