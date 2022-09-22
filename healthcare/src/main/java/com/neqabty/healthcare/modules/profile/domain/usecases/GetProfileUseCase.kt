package com.neqabty.healthcare.modules.profile.domain.usecases

import com.neqabty.healthcare.modules.profile.data.model.AddFollowerBody
import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.modules.profile.domain.entity.relations.RelationEntityList
import com.neqabty.healthcare.modules.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(phone: String): Flow<ProfileEntity>{
        return profileRepository.getProfile(phone)
    }

    fun build(): Flow<List<RelationEntityList>>{
        return profileRepository.getRelations()
    }

    fun build(addFollowerBody: AddFollowerBody): Flow<String>{
        return profileRepository.addFollower(addFollowerBody)
    }

    fun build(followerId: Int, subscriberId: String): Flow<Boolean>{
        return profileRepository.deleteFollower(followerId, subscriberId)
    }

}