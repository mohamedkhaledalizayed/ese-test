package com.neqabty.healthcare.chefaa.home.domain.usecase

import com.neqabty.healthcare.chefaa.home.domain.entities.RegistrationEntity
import com.neqabty.healthcare.chefaa.home.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    fun build(phoneNumber:String,userId:String,countryCode:String,nationalId:String, name:String): Flow<RegistrationEntity> {
        return registerRepository.registerUser(phoneNumber, userId,countryCode,nationalId,name)
    }
}