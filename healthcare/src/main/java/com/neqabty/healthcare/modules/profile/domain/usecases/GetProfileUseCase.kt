package com.neqabty.healthcare.modules.profile.domain.usecases

import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.modules.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(phone: String): Flow<ProfileEntity>{
        return profileRepository.getProfile(phone)
    }
}