package com.neqabty.chefaa.modules.home.domain.usecase

import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(phoneNumber:String,userId:String,countryCode:String,nationalId:String, name:String): Flow<RegistrationEntity> {
        return homeRepository.registerUser(phoneNumber, userId,countryCode,nationalId,name)
    }
}