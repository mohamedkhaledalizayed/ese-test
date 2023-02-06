package com.neqabty.healthcare.sustainablehealth.mypackages.domain.usecases

import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.addfollower.AddFollowerEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.relations.RelationEntityList
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(phone: String): Flow<ProfileEntity>{
        return profileRepository.getProfile(phone)
    }

    fun build(): Flow<List<RelationEntityList>>{
        return profileRepository.getRelations()
    }

    fun build(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>{
        return profileRepository.addFollower(addFollowerBody)
    }

    fun build(followerId: Int, subscriberId: String, mobile: String): Flow<Boolean>{
        return profileRepository.deleteFollower(followerId, subscriberId, mobile)
    }

}